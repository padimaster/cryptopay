// Shared Preferences Manager
package padilla.alex.cryptopay.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "cryptopay_prefs"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_FACE_VERIFIED = "face_verified"
    }

    // Auth Token
    fun saveAuthToken(token: String) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearAuthToken() {
        sharedPreferences.edit().remove(KEY_AUTH_TOKEN).apply()
    }

    // User Email
    fun saveUserEmail(email: String) {
        sharedPreferences.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    // Login Status
    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // User ID
    fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    // Face Verification
    fun setFaceVerified(verified: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_FACE_VERIFIED, verified).apply()
    }

    fun isFaceVerified(): Boolean {
        return sharedPreferences.getBoolean(KEY_FACE_VERIFIED, false)
    }

    // Clear all data
    fun clearAllData() {
        sharedPreferences.edit().clear().apply()
    }
}