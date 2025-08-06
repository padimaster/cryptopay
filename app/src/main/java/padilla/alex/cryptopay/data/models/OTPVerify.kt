// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

// Request Models
data class OTPVerify(
    @SerializedName("otp") val otp: String,
    @SerializedName("token") val token: String
)