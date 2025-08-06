// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

//Specific Response Models
data class ValidateCodeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String? = null
)