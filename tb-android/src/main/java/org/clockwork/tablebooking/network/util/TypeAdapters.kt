package org.clockwork.tablebooking.network.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.Instant

class InstantTypeAdapter() : TypeAdapter<Instant>() {
    override fun write(p0: JsonWriter?, p1: Instant?) {
        p1?.let {
            p0!!.value(p1.toString())
        } ?: p0!!.nullValue()
    }

    override fun read(p0: JsonReader?): Instant? {
        p0!!.apply {
            if (peek() == JsonToken.NULL) {
                nextNull()
                return null
            } else {
                return Instant.parse(nextString())
            }
        }
    }

}