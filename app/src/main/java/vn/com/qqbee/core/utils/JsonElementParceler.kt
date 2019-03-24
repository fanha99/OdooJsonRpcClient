package vn.com.qqbee.core.utils

import android.os.Parcel
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import vn.com.qqbee.toJsonElement
import kotlinx.android.parcel.Parceler

object JsonElementParceler : Parceler<JsonElement> {
    override fun create(parcel: Parcel): JsonElement = parcel.readString()?.toJsonElement() ?: JsonArray()


    override fun JsonElement.write(parcel: Parcel, flags: Int) {
        parcel.writeString(this.toString())
    }
}