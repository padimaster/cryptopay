// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

//Specific Response Models
data class BalanceResponse(
    @SerializedName("balance") val balance: Double,
    @SerializedName("currency") val currency: String
)