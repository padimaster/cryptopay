// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

// Request Models
data class SendRequest(
    @SerializedName("token") val token: String,
    @SerializedName("to_address") val toAddress: String,
    @SerializedName("amount") val amount: Double
)