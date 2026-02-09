package me.hd.wexpt.hook.hooker

import android.net.Uri
import android.widget.FrameLayout
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.factory.injectModuleAppResources
import me.hd.wexpt.hook.HostData.toAppClass
import me.hd.wexpt.hook.wrapper.WebViewWrapper
import me.hd.wexpt.misc.webview.WebConfig
import me.hd.wexpt.misc.webview.WebJsApi
import me.hd.wexpt.misc.wrapper.ConfigWrapper

object SettingHooker : YukiBaseHooker() {
    private val uin by lazy {
        ConfigWrapper.get("system_config_prefs").getUin()
    }

    override fun onHook() {
        "com.tencent.mm.ui.widget.MMWebView".toAppClass().resolve().firstMethod {
            name = "loadUrl"
            parameters(String::class)
        }.hook {
            before {
                val url = args(0).cast<String>()
                if (Uri.parse(url).host == Uri.parse(WebConfig.URL).host) {
                    val webView = instance<FrameLayout>().apply { context.injectModuleAppResources() }
                    val webViewWrapper = WebViewWrapper.get(webView)
                    webViewWrapper.addJavascriptInterface(WebJsApi(uin), "WExpt")
                    webViewWrapper.loadDataWithBaseURL("file:///android_asset/", WebConfig.getHtml("index.html"))
                    resultNull()
                }
            }
        }
    }
}
