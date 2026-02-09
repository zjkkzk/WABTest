package me.hd.wexpt.misc.webview

import com.highcapable.yukihookapi.hook.factory.injectModuleAppResources
import me.hd.wexpt.hook.HostData
import me.hd.wexpt.misc.ext.readAssetsText

object WebConfig {
    const val URL = "http://wexpt.weixin.com/"

    fun getHtml(path: String): String {
        val ctx = HostData.appContext
        ctx.injectModuleAppResources()
        return ctx.readAssetsText(path)
    }
}
