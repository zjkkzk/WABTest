package me.hd.wexpt.misc.webview

import com.highcapable.yukihookapi.hook.factory.injectModuleAppResources
import me.hd.wexpt.hook.HostData
import me.hd.wexpt.misc.ext.readAssetsText

object WebConfig {
    const val URL = "http://wexpt.weixin.com/"

    val html: String by lazy {
        val ctx = HostData.appContext
        ctx.injectModuleAppResources()
        ctx.readAssetsText("setting/index.html")
    }
}
