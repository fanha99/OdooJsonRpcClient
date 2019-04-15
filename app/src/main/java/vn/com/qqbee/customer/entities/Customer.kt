package vn.com.qqbee.customer.entities

import android.os.Parcelable
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.com.qqbee.core.utils.JsonElementParceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler

@Parcelize
data class Customer(

    @Expose
    @SerializedName("id")
    var id: Long = 0,

    @Expose
    @SerializedName("name")
    var name: String = "false",

    @Expose
    @SerializedName("email")
    var email: String = "false",

    @Expose
    @SerializedName("company_name")
    var companyName: String = "false",

    @Expose
    @SerializedName("parent_name")
    var parentName: String = "false",

    @Expose
    @SerializedName("image_small")
    var imageSmall: String = "false",

    @Expose
    @SerializedName("website")
    var website: String = "false",

    @Expose
    @SerializedName("phone")
    var phone: String = "false",

    @Expose
    @SerializedName("mobile")
    var mobile: String = "false",

    @Expose
    @SerializedName("full_address")
    var fullAddress: String = "false",

    @Expose
    @SerializedName("state_id")
    @TypeParceler<JsonElement, JsonElementParceler>
    var stateId: JsonElement = JsonArray(),

    @Expose
    @SerializedName("country_id")
    @TypeParceler<JsonElement, JsonElementParceler>
    var countryId: JsonElement = JsonArray(),

    @Expose
    @SerializedName("comment")
    var comment: String = "false",

    @Expose
    @SerializedName("is_company")
    var isCompany: Boolean = false

) : Parcelable {
    companion object {
        @JvmField
        val fieldsMap: Map<String, String> = mapOf(
            "id" to "id", "name" to "Name", "email" to "Email", "parent_name" to "Parent name",
            "company_name" to "Company Name", "image_small" to "Image", "website" to "Website",
            "phone" to "Phone Number", "mobile" to "Mobile Number",/* "full_address" to "Full Address",*/
            "state_id" to "State", "country_id" to "Country", "comment" to "Internal Note",
            "is_company" to "Is Company"
        )

        @JvmField
        val fields: ArrayList<String> = fieldsMap.keys.toMutableList() as ArrayList<String>
    }
}