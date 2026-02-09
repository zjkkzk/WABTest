package me.hd.wexpt.misc.ext

import android.content.Context

fun Context.readAssetsText(path: String): String {
    return assets.open(path).use { input ->
        input.readBytes().toString(Charsets.UTF_8)
    }
}
