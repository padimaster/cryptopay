// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

// Request Models
data class ValidateFaceRequest(
    @SerializedName("token") val token: String,
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("country") val country: String,
    @SerializedName("document_file") val documentFile: String, // Base64 string
    @SerializedName("face_file") val faceFile: String // Base64 string
)