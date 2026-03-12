package me.hd.wexpt.misc.webview

import android.webkit.JavascriptInterface
import me.hd.wexpt.core.expt.ExptAppItem
import me.hd.wexpt.core.expt.ExptAppManager
import me.hd.wexpt.misc.util.JsonUtil

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
