// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

//Specific Response Models
data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user_id") val userId: String? = null
)