package me.hd.wexpt.core

import me.hd.wexpt.misc.util.JsonUtil
import me.hd.wexpt.misc.wrapper.MMKVWrapper

class AppManager(uin: Int) {
    private val appKeyMmkv by lazy {
        MMKVWrapper.get("${uin}_WxExptAppKeyMmkv")
    }

    private val appIdMmkv by lazy {
        MMKVWrapper.get("${uin}_WxExptAppIdMmkv")
    }

    private val fakeExptId = 99999

    fun putAppItemArgs(args: List<AppItem.Arg>) {
        args.forEach { arg -> appKeyMmkv.putInt(arg.key, fakeExptId) }
        val appItem = AppItem(fakeExptId, args = args)
        appIdMmkv.putString(fakeExptId.toString(), JsonUtil.toJson(appItem))
    }

    fun getAppItemArgs(): List<AppItem.Arg> {
        val defAppItem = AppItem(fakeExptId)
        val jsonAppItem = appIdMmkv.getString(fakeExptId.toString(), JsonUtil.toJson(defAppItem))
        val objAppItem = JsonUtil.fromJson<AppItem>(jsonAppItem)
        return objAppItem.args
    }
}
