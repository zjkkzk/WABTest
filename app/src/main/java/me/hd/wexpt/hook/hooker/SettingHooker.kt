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
                    webViewWrapper.addJavascriptInterface(WebJsApi, "WExpt")
                    webViewWrapper.loadDataWithBaseURL("file:///android_asset/", WebConfig.getHtml("index.html"))
                    resultNull()
                }
            }
        }
    }
}
