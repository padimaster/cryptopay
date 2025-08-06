// Repository Pattern
package padilla.alex.cryptopay.repository

import padilla.alex.cryptopay.data.models.*
import padilla.alex.cryptopay.network.NetworkConfig
import retrofit2.Response

class CryptoPayRepository {

    private val apiService = NetworkConfig.apiService

    // Send Verification Code
    suspend fun sendVerificationCode(email: String): ApiResult<SendCodeResponse> {
        return try {
            val response = apiService.sendVerificationCode(OTPRequest(email))
            handleApiResponse(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido")
        }
    }

    // Validate Code
    suspend fun validateCode(otp: String, token: String): ApiResult<ValidateCodeResponse> {
        return try {
            val response = apiService.validateCode(OTPVerify(otp, token))
            handleApiResponse(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido")
        }
    }

    // Validate Face
    suspend fun validateFace(
        token: String,
        name: String,
        lastName: String,
        country: String,
        documentFile: String,
        faceFile: String
    ): ApiResult<ValidateFaceResponse> {
        return try {
            val request = ValidateFaceRequest(token, name, lastName, country, documentFile, faceFile)
            val response = apiService.validateFace(request)
            handleApiResponse(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido")
        }
    }

    // Send Login Code
    suspend fun sendLoginCode(email: String): ApiResult<SendCodeResponse> {
        return try {
            val response = apiService.sendLoginCode(LoginRequest(email))
            handleApiResponse(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido")
        }
    }

    // Validate Login
    suspend fun validateLogin(otp: String, token: String): ApiResult<LoginResponse> {
        return try {
            val response = apiService.validateLogin(LoginVerify(otp, token))
            handleApiResponse(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido")
        }
    }

    // Generate QR
    suspend fun generateQR(token: String): ApiResult<QRResponse> {
        return try {
            val response = apiService.generateQR(QRRequest(token))
            handleApiResponse(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido")
        }
    }

    // Get Balance
    suspend fun getBalance(token: String): ApiResult<BalanceResponse> {
        return try {
            val response = apiService.getBalance("Bearer $token")
            handleApiResponse(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido")
        }
    }

    // Send ETH
    suspend fun sendETH(token: String, toAddress: String, amount: Double): ApiResult<TransactionResponse> {
        return try {
            val request = SendRequest(token, toAddress, amount)
            val response = apiService.sendETH(request)
            handleApiResponse(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido")
        }
    }

    // Handle API Response
    private fun <T> handleApiResponse(response: Response<ApiResponse<T>>): ApiResult<T> {
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            if (apiResponse?.success == true && apiResponse.data != null) {
                ApiResult.Success(apiResponse.data)
            } else {
                ApiResult.Error(apiResponse?.message ?: "Error en la respuesta")
            }
        } else {
            ApiResult.Error("Error HTTP: ${response.code()}")
        }
    }
}

// Sealed class para manejar los resultados de la API
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}