// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

//Specific Response Models
data class QRResponse(
    @SerializedName("qr_code") val qrCode: String,
    @SerializedName("address") val address: String
)