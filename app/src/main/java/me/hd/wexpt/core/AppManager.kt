package me.hd.wexpt.core

import me.hd.wexpt.misc.wrapper.MMKVWrapper

class AppManager(uin: Int) {
    private val appKeyMmkv by lazy {
        MMKVWrapper.get("${uin}_WxExptAppKeyMmkv")
    }

    private val appIdMmkv by lazy {
        MMKVWrapper.get("${uin}_WxExptAppIdMmkv")
    }

    fun putAppItem(appItem: AppItem) {
        appKeyMmkv.apply {
            appItem.args.forEach { arg ->
                putInt(arg.key, appItem.exptId)
            }
        }
        appIdMmkv.apply {
            putString(appItem.exptId.toString(), appItem.toString())
        }
    }
}
