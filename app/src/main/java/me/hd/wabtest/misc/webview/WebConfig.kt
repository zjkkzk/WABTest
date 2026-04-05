package me.hd.wabtest.misc.webview

import com.highcapable.yukihookapi.hook.factory.injectModuleAppResources
import me.hd.wabtest.hook.HostData
import me.hd.wabtest.misc.ext.readAssetsText

object WebConfig {
    const val URL = "http://abtest.weixin.com/"

    fun getHtml(path: String): String {
        val ctx = HostData.appContext
        ctx.injectModuleAppResources()
        return ctx.readAssetsText(path)
    }
}
