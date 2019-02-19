package vn.com.qqbee.core.entities.dataset.load

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.com.qqbee.core.entities.odooError.OdooError

data class Load(

        @field:Expose
        @field:SerializedName("result")
        val result: LoadResult = LoadResult(),

        @field:Expose
        @field:SerializedName("error")
        val odooError: OdooError = OdooError()

) {
    val isSuccessful get() = !isOdooError
    val isOdooError get() = odooError.message.isNotEmpty()
    val errorCode get() = odooError.code
    val errorMessage get() = odooError.data.message
}