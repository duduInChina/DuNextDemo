package com.dudu.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.dudu.data.datastore.WeatherPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 *  使用 Protocol Buffers（protobuf）的优势包括：
 *      高效的序列化和反序列化：Protocol Buffers 使用二进制格式，因此相比于 XML 和 JSON 等文本格式，它们在序列化和反序列化时通常更加高效。
 *       跨语言支持：Protocol Buffers 支持多种编程语言，因此您可以使用不同的编程语言来处理相同的数据结构。
 */
class WeatherPreferencesSerializer @Inject constructor() : Serializer<WeatherPreferences> {

    override val defaultValue: WeatherPreferences = WeatherPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): WeatherPreferences =
        try {
            // readFrom is already called on the data store background thread
            // readFrom在data store background thread线程上被调用
            WeatherPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: WeatherPreferences, output: OutputStream) {
        t.writeTo(output)
    }

}