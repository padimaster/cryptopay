// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

// Specific Response Models
data class ValidateFaceResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("confidence") val confidence: Double? = null,
    @SerializedName("verified") val verified: Boolean
)