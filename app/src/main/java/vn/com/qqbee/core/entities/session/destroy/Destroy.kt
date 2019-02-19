package vn.com.qqbee.core.entities.session.destroy

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.com.qqbee.core.entities.odooError.OdooError

data class Destroy(

        @field:Expose
        @field:SerializedName("error")
        val odooError: OdooError = OdooError()

) {
    val isSuccessful get() = !isOdooError
    val isOdooError get() = odooError.message.isNotEmpty()
    val errorCode get() = odooError.code
    val errorMessage get() = odooError.data.message
}
