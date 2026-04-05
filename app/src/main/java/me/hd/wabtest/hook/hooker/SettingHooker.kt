package me.hd.wabtest.hook.hooker

import android.net.Uri
import android.widget.FrameLayout
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.factory.injectModuleAppResources
import me.hd.wabtest.hook.HostData.toAppClass
import me.hd.wabtest.hook.wrapper.WebViewWrapper
import me.hd.wabtest.misc.webview.WebConfig
import me.hd.wabtest.misc.webview.WebJsApi

object SettingHooker : YukiBaseHooker() {
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
                    webViewWrapper.addJavascriptInterface(WebJsApi, "WABTest")
                    webViewWrapper.loadDataWithBaseURL("file:///android_asset/", WebConfig.getHtml("index.html"))
                    resultNull()
                }
            }
        }
    }
}
