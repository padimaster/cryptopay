// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

// Response Models
data class ValidationError(
    @SerializedName("loc") val location: List<Any>,
    @SerializedName("msg") val message: String,
    @SerializedName("type") val type: String
)