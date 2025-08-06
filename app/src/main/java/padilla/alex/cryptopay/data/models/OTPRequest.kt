// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

// Request Models
data class OTPRequest(
    @SerializedName("email") val email: String
)
