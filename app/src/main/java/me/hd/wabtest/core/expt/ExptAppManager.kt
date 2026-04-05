package me.hd.wabtest.core.expt

import me.hd.wabtest.misc.util.JsonUtil
import me.hd.wabtest.misc.wrapper.ConfigWrapper
import me.hd.wabtest.misc.wrapper.MMKVWrapper

object ExptAppManager {
    private val uin by lazy { ConfigWrapper.get("system_config_prefs").getUin() }

    private val appKeyMmkv by lazy { MMKVWrapper.get("${uin}_WxExptAppKeyMmkv") }

    private val appIdMmkv by lazy { MMKVWrapper.get("${uin}_WxExptAppIdMmkv") }

    private const val FAKE_EXPT_ID = 99999

    fun putAppItemArgs(args: List<ExptAppItem.Arg>) {
        args.forEach { arg -> appKeyMmkv.putInt(arg.key, FAKE_EXPT_ID) }
        val appItem = ExptAppItem(FAKE_EXPT_ID, args = args)
        appIdMmkv.putString(FAKE_EXPT_ID.toString(), JsonUtil.toJson(appItem))
    }

    fun getAppItemArgs(): List<ExptAppItem.Arg> {
        val defAppItem = ExptAppItem(FAKE_EXPT_ID)
        val jsonAppItem = appIdMmkv.getString(FAKE_EXPT_ID.toString(), JsonUtil.toJson(defAppItem))
        val objAppItem = JsonUtil.fromJson<ExptAppItem>(jsonAppItem)
        return objAppItem.args
    }
}
