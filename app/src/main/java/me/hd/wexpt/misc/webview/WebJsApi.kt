package me.hd.wexpt.misc.webview

import android.webkit.JavascriptInterface
import me.hd.wexpt.core.AppItem
import me.hd.wexpt.core.AppManager
import me.hd.wexpt.misc.util.JsonUtil

class WebJsApi(uin: Int) {
    private val appManager = AppManager(uin)

    @JavascriptInterface
    fun putExptArgs(jsonArrayStr: String) {
        val args = JsonUtil.fromJson<List<AppItem.Arg>>(jsonArrayStr)
        appManager.putAppItem(AppItem(exptId = 99999, args = args))
    }
}
