plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val vueProjectDir = file("src/main/vue/wabtest")
val vueDistDir = file("$vueProjectDir/dist")
val npmCmd = if (System.getProperty("os.name").startsWith("Windows")) "npm.cmd" else "npm"

android {
    namespace = "me.hd.wabtest"
    compileSdk = 36

    sourceSets {
        named("main") {
            assets {
                srcDirs(vueDistDir.path)
            }
        }
    }

    defaultConfig {
        applicationId = "me.hd.wabtest"
        minSdk = 27
        targetSdk = 36
        versionCode = 26040520
        versionName = "1.0.7"
        buildConfigField("String", "APP_NAME", "\"WABTest\"")
    }

    androidResources {
        additionalParameters += listOf("--allow-reserved-package-id", "--package-id", "0x78")
    }

    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packaging {
        resources.excludes.addAll(
            arrayOf(
                "kotlin/**",
                "META-INF/**",
                "**.bin",
                "kotlin-tooling-metadata.json",
            )
        )
    }
    applicationVariants.all {
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output.outputFileName?.let { fileName ->
                if (fileName.endsWith(".apk")) {
                    val projectName = rootProject.name
                    val versionName = defaultConfig.versionName
                    output.outputFileName = "${projectName}_v${versionName}.apk"
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    jvmToolchain(17)
}

val npmInstallVueDeps = tasks.register("npmInstallVueDeps") {
    group = "wabtest"
    description = "Install dependencies for Vue settings page"
    inputs.files(file("$vueProjectDir/package.json"), file("$vueProjectDir/package-lock.json"))
    outputs.dir(file("$vueProjectDir/node_modules"))
    doLast {
        exec {
            workingDir = vueProjectDir
            commandLine(npmCmd, "install")
        }
    }
}

val buildVueSettingsPage = tasks.register("buildVueSettingsPage") {
    group = "wabtest"
    description = "Build Vue settings page into Android assets"
    dependsOn(npmInstallVueDeps)
    inputs.dir(file("$vueProjectDir/src"))
    inputs.file(file("$vueProjectDir/public/index.html"))
    inputs.file(file("$vueProjectDir/vue.config.js"))
    outputs.dir(vueDistDir)
    doLast {
        exec {
            workingDir = vueProjectDir
            commandLine(npmCmd, "run", "build")
        }
    }
}

tasks.preBuild {
    dependsOn(buildVueSettingsPage)
}

dependencies {
    implementation(libs.annotation)
    compileOnly(libs.xposed.api)
    implementation(libs.yukihookapi.api)
    ksp(libs.yukihookapi.ksp.xposed)
    implementation(libs.kavaref.core)
    implementation(libs.kavaref.extension)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.protobuf)
}
