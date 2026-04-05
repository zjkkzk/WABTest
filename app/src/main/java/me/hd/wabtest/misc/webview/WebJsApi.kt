package me.hd.wabtest.misc.webview

import android.webkit.JavascriptInterface
import me.hd.wabtest.core.expt.ExptAppItem
import me.hd.wabtest.core.expt.ExptAppManager
import me.hd.wabtest.misc.util.JsonUtil

object WebJsApi {
    @JavascriptInterface
    fun putExptArgs(str: String) {
        val args = JsonUtil.fromJson<List<ExptAppItem.Arg>>(str)
        ExptAppManager.putAppItemArgs(args)
    }

    @JavascriptInterface
    fun getExptArgs(): String {
        val args = ExptAppManager.getAppItemArgs()
        return JsonUtil.toJson(args)
    }
}
