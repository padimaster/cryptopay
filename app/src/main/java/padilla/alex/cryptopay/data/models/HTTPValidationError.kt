// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

// Response Models
data class HTTPValidationError(
    @SerializedName("detail") val detail: List<ValidationError>
)