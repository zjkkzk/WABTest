@file:OptIn(ExperimentalSerializationApi::class)

package me.hd.wexpt.misc.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf

object PbUtil {
    val pb = ProtoBuf {
        encodeDefaults = true
    }

    inline fun <reified T> toPb(value: T): ByteArray {
        return pb.encodeToByteArray(value)
    }

    inline fun <reified T> fromPb(value: ByteArray): T {
        return pb.decodeFromByteArray(value)
    }

    inline fun <reified T> toWxPb(value: T): ByteArray {
        val body = pb.encodeToByteArray(value)
        val head = encodeVarint32(body.size)
        return head + body
    }

    fun encodeVarint32(value: Int): ByteArray {
        var v = value
        val out = ArrayList<Byte>(5)
        while ((v and 0x7F.inv()) != 0) {
            out.add(((v and 0x7F) or 0x80).toByte())
            v = v ushr 7
        }
        out.add(v.toByte())
        return out.toByteArray()
    }
}
