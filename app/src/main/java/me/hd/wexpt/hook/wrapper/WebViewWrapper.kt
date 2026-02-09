package me.hd.wexpt.hook.wrapper

import com.highcapable.kavaref.KavaRef.Companion.asResolver

class WebViewWrapper private constructor(private val webView: Any) {
    companion object {
        fun get(iWebView: Any): WebViewWrapper {
            return WebViewWrapper(iWebView)
        }
    }

    fun addJavascriptInterface(obj: Any, tag: String) {
        webView.asResolver().firstMethod {
            name = "addJavascriptInterface"
            parameters(Any::class, String::class)
            superclass()
        }.invoke(obj, tag)
    }

    fun loadDataWithBaseURL(baseUrl: String?, data: String, mimeType: String = "text/html", encoding: String = "UTF-8", historyUrl: String? = null) {
        webView.asResolver().firstMethod {
            name = "loadDataWithBaseURL"
            parameters(String::class, String::class, String::class, String::class, String::class)
            superclass()
        }.invoke(baseUrl, data, mimeType, encoding, historyUrl)
    }
}
