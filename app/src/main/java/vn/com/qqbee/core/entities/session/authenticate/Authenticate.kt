package vn.com.qqbee.core.entities.session.authenticate

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.com.qqbee.core.entities.odooError.OdooError

data class Authenticate(

        @field:Expose
        @field:SerializedName("result")
        var result: AuthenticateResult = AuthenticateResult(),

        @field:Expose
        @field:SerializedName("error")
        var odooError: OdooError = OdooError()
) {
    val isSuccessful get() = !isOdooError
    val isOdooError get() = odooError.message.isNotEmpty()
    val errorCode get() = odooError.code
    val errorMessage get() = odooError.data.message
}