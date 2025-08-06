// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

// Response Models
data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: T? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("error") val error: HTTPValidationError? = null
)