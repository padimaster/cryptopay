// Utilities
package padilla.alex.cryptopay.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream


object Utils {

    // Convert Bitmap to Base64 String
    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // Convert Uri to Base64 String
    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            if (bytes != null) {
                Base64.encodeToString(bytes, Base64.DEFAULT)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Show Toast
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    // Show Long Toast
    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    // Validate Email
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Validate OTP (assuming 6 digits)
    fun isValidOTP(otp: String): Boolean {
        return otp.length == 6 && otp.all { it.isDigit() }
    }

    // Format Balance
    fun formatBalance(balance: Double): String {
        return String.format("%.4f", balance)
    }

    // Validate Ethereum Address
    fun isValidEthAddress(address: String): Boolean {
        return address.matches(Regex("^0x[a-fA-F0-9]{40}$"))
    }
}

// Extension functions
fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.IO) {
        block()
    }
}

fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.Main) {
        block()
    }
}

suspend fun <T> CoroutineScope.withIO(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO) {
        block()
    }
}