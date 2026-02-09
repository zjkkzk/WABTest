package me.hd.wexpt.misc.webview

import android.webkit.JavascriptInterface
import me.hd.wexpt.core.AppItem
import me.hd.wexpt.core.AppManager
import me.hd.wexpt.misc.util.JsonUtil

class WebJsApi(uin: Int) {
    private val appManager = AppManager(uin)

    @JavascriptInterface
    fun putExptArgs(str: String) {
        val args = JsonUtil.fromJson<List<AppItem.Arg>>(str)
        appManager.putAppItemArgs(args)
    }

    @JavascriptInterface
    fun getExptArgs(): String {
        val args = appManager.getAppItemArgs()
        return JsonUtil.toJson(args)
    }
}
