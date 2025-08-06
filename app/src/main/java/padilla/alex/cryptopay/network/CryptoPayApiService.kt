// API Service Interface
package padilla.alex.cryptopay.network

import padilla.alex.cryptopay.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface CryptoPayApiService {

    @POST("/api/send_verification_code")
    suspend fun sendVerificationCode(
        @Body request: OTPRequest
    ): Response<ApiResponse<SendCodeResponse>>

    @POST("/api/validate_code")
    suspend fun validateCode(
        @Body request: OTPVerify
    ): Response<ApiResponse<ValidateCodeResponse>>

    @POST("/api/validate_face")
    suspend fun validateFace(
        @Body request: ValidateFaceRequest
    ): Response<ApiResponse<ValidateFaceResponse>>

    @POST("/api/send_login_code")
    suspend fun sendLoginCode(
        @Body request: LoginRequest
    ): Response<ApiResponse<SendCodeResponse>>

    @POST("/api/validate_login")
    suspend fun validateLogin(
        @Body request: LoginVerify
    ): Response<ApiResponse<LoginResponse>>

    @POST("/api/generate_qr")
    suspend fun generateQR(
        @Body request: QRRequest
    ): Response<ApiResponse<QRResponse>>

    @GET("/api/get_balance")
    suspend fun getBalance(
        @Header("Authorization") token: String
    ): Response<ApiResponse<BalanceResponse>>

    @POST("/api/send_eth")
    suspend fun sendETH(
        @Body request: SendRequest
    ): Response<ApiResponse<TransactionResponse>>
}