package me.hd.wexpt.core.brand

import me.hd.wexpt.core.brand.proto.EcsEntranceInfo
import me.hd.wexpt.core.brand.proto.toByteArray
import me.hd.wexpt.misc.wrapper.MMKVWrapper

object BrandServiceManager {
    private val brandServiceMmkv by lazy { MMKVWrapper.get("brandService_accounts") }

    fun putEntranceInfo() {
        brandServiceMmkv.putBytes(
            "key_ecs_card_holder_entrance_info",
            EcsEntranceInfo().toByteArray()
        )
    }
}
