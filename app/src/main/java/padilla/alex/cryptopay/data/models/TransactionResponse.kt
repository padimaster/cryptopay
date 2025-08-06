// Data Classes para las APIs
package padilla.alex.cryptopay.data.models

import com.google.gson.annotations.SerializedName

//Specific Response Models
data class TransactionResponse(
    @SerializedName("transaction_hash") val transactionHash: String,
    @SerializedName("status") val status: String
)