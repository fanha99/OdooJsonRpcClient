package vn.com.qqbee.core.persistence

import androidx.room.TypeConverter
import com.google.gson.JsonElement
import vn.com.qqbee.toJsonElement

class AppTypeConverters {

    @TypeConverter
    fun fromJsonElement(jsonElement: JsonElement): String = jsonElement.toString()

    @TypeConverter
    fun stringToJsonElement(string: String): JsonElement = string.toJsonElement()
}
