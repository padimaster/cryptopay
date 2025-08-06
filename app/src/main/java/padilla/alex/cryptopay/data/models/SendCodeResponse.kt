// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

//Specific Response Models
data class SendCodeResponse(
    @SerializedName("token") val token: String,
    @SerializedName("message") val message: String
)