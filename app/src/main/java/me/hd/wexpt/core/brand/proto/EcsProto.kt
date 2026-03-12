@file:OptIn(ExperimentalSerializationApi::class)

package me.hd.wexpt.core.brand.proto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.protobuf.ProtoNumber

fun EcsEntranceInfo.toByteArray() = ProtoBuf.encodeToByteArray(this)

@Serializable
data class EcsEntranceInfo(
    @ProtoNumber(1) var showType: Int = 1,
    @ProtoNumber(2) var entrance: EcsEntrance = EcsEntrance(),
    @ProtoNumber(3) var needPreload: Int = 0,
    @ProtoNumber(4) var iconUrl: String = "",
    @ProtoNumber(5) var titleType: Int = 0,
)

@Serializable
data class EcsEntrance(
    @ProtoNumber(2) var weApp: EcsWeApp = EcsWeApp(),
)

@Serializable
data class EcsWeApp(
    @ProtoNumber(12) var flag12: Int = 1,
)
