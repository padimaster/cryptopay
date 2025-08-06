package padilla.alex.cryptopay

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import padilla.alex.cryptopay.repository.ApiResult
import padilla.alex.cryptopay.repository.CryptoPayRepository
import padilla.alex.cryptopay.utils.PreferencesManager
import padilla.alex.cryptopay.utils.Utils

class LoginActivity : AppCompatActivity() {

    // UI Elements - usando exactamente los IDs de tu layout
    private lateinit var initialLoginButton: MaterialButton
    private lateinit var loginCard: MaterialCardView
    private lateinit var backButton: ImageButton
    private lateinit var loginTitle: TextView
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var continueButton: MaterialButton

    // OTP Elements
    private lateinit var otpGroup: Group
    private lateinit var otpIllustration: ImageView
    private lateinit var otpInfo: TextView
    private lateinit var otpEmail: TextView
    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText
    private lateinit var otpFeedbackText: TextView
    private lateinit var resendRow: LinearLayout
    private lateinit var resendHint: TextView
    private lateinit var resendTimer: TextView
    private lateinit var verifyButton: MaterialButton

    // Success Elements
    private lateinit var successGroup: Group
    private lateinit var successIcon: ImageView
    private lateinit var successTitle: TextView
    private lateinit var successEmail: TextView

    // Repository and Utils
    private val repository = CryptoPayRepository()
    private lateinit var preferencesManager: PreferencesManager

    // State variables
    private var currentToken: String? = null
    private var otpFields: List<EditText> = emptyList()
    private var countDownTimer: CountDownTimer? = null
    private var isInitialState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setupListeners()
        preferencesManager = PreferencesManager(this)

        // Verificar si ya está logueado
        if (preferencesManager.isLoggedIn()) {
            navigateToHome()
        }
    }

    private fun initViews() {
        // Main elements
        initialLoginButton = findViewById(R.id.initialLoginButton)
        loginCard = findViewById(R.id.loginCard)
        backButton = findViewById(R.id.backButton)
        loginTitle = findViewById(R.id.loginTitle)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        emailEditText = findViewById(R.id.emailEditText)
        continueButton = findViewById(R.id.continueButton)

        // OTP elements
        otpGroup = findViewById(R.id.otpGroup)
        otpIllustration = findViewById(R.id.otpIllustration)
        otpInfo = findViewById(R.id.otpInfo)
        otpEmail = findViewById(R.id.otpEmail)
        otp1 = findViewById(R.id.otp1)
        otp2 = findViewById(R.id.otp2)
        otp3 = findViewById(R.id.otp3)
        otp4 = findViewById(R.id.otp4)
        otp5 = findViewById(R.id.otp5)
        otp6 = findViewById(R.id.otp6)
        otpFeedbackText = findViewById(R.id.otpFeedbackText)
        resendRow = findViewById(R.id.resendRow)
        resendHint = findViewById(R.id.resendHint)
        resendTimer = findViewById(R.id.resendTimer)
        verifyButton = findViewById(R.id.verifyButton)

        // Success elements
        successGroup = findViewById(R.id.successGroup)
        successIcon = findViewById(R.id.successIcon)
        successTitle = findViewById(R.id.successTitle)
        successEmail = findViewById(R.id.successEmail)

        // Initialize OTP fields list
        otpFields = listOf(otp1, otp2, otp3, otp4, otp5, otp6)

        setupOtpFields()
    }

    private fun setupListeners() {
        initialLoginButton.setOnClickListener {
            showLoginCard()
        }

        backButton.setOnClickListener {
            if (!isInitialState) {
                showInitialState()
            }
        }

        continueButton.setOnClickListener {
            sendLoginCode()
        }

        verifyButton.setOnClickListener {
            verifyLoginCode()
        }

        resendRow.setOnClickListener {
            if (countDownTimer == null) {
                sendLoginCode()
            }
        }
    }

    private fun setupOtpFields() {
        otpFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        // Move to next field
                        if (index < otpFields.size - 1) {
                            otpFields[index + 1].requestFocus()
                        }
                    } else if (s?.length == 0) {
                        // Move to previous field
                        if (index > 0) {
                            otpFields[index - 1].requestFocus()
                        }
                    }

                    // Check if all fields are filled
                    val allFilled = otpFields.all { it.text.isNotEmpty() }
                    verifyButton.isEnabled = allFilled

                    if (allFilled) {
                        clearOtpError()
                    }
                }
            })
        }
    }

    private fun showLoginCard() {
        loginCard.visibility = View.VISIBLE
        loginCard.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(300)
            .start()

        initialLoginButton.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    initialLoginButton.visibility = View.GONE
                }
            })
            .start()

        isInitialState = false
    }

    private fun showInitialState() {
        loginCard.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    loginCard.visibility = View.GONE
                    resetToInitialState()
                }
            })
            .start()

        initialLoginButton.visibility = View.VISIBLE
        initialLoginButton.animate()
            .alpha(1f)
            .setDuration(300)
            .start()

        isInitialState = true
    }

    private fun resetToInitialState() {
        otpGroup.visibility = View.GONE
        successGroup.visibility = View.GONE
        backButton.visibility = View.GONE
        emailEditText.text?.clear()
        clearOtpFields()
        clearOtpError()
        countDownTimer?.cancel()
        countDownTimer = null
    }

    private fun sendLoginCode() {
        val email = emailEditText.text.toString().trim()

        if (!Utils.isValidEmail(email)) {
            emailInputLayout.error = "Por favor ingresa un email válido"
            return
        }

        emailInputLayout.error = null
        continueButton.isEnabled = false

        lifecycleScope.launch {
            when (val result = repository.sendLoginCode(email)) {
                is ApiResult.Success -> {
                    continueButton.isEnabled = true
                    currentToken = result.data.token
                    preferencesManager.saveUserEmail(email)
                    showOtpSection(email)
                    Utils.showToast(this@LoginActivity, "Código enviado exitosamente")
                }
                is ApiResult.Error -> {
                    continueButton.isEnabled = true
                    Utils.showToast(this@LoginActivity, result.message)
                }
                is ApiResult.Loading -> {
                    // Loading state handled by button disable
                }
            }
        }
    }

    private fun showOtpSection(email: String) {
        otpEmail.text = email
        otpGroup.visibility = View.VISIBLE
        backButton.visibility = View.VISIBLE

        // Start countdown timer
        startResendTimer()

        // Focus on first OTP field
        otp1.requestFocus()
    }

    private fun verifyLoginCode() {
        val otp = getOtpCode()

        if (otp.length != 6) {
            showOtpError("Por favor ingresa el código completo")
            return
        }

        if (currentToken == null) {
            Utils.showToast(this, "Error: Token no encontrado. Solicita un nuevo código.")
            return
        }

        verifyButton.isEnabled = false

        lifecycleScope.launch {
            when (val result = repository.validateLogin(otp, currentToken!!)) {
                is ApiResult.Success -> {
                    verifyButton.isEnabled = true

                    // Guardar datos del usuario
                    preferencesManager.saveAuthToken(result.data.token)
                    result.data.userId?.let { preferencesManager.saveUserId(it) }
                    preferencesManager.setLoggedIn(true)

                    showSuccessSection()

                    // Navigate to home after delay
                    android.os.Handler(mainLooper).postDelayed({
                        navigateToHome()
                    }, 2000)
                }
                is ApiResult.Error -> {
                    verifyButton.isEnabled = true
                    showOtpError(result.message)
                    clearOtpFields()
                }
                is ApiResult.Loading -> {
                    // Loading state handled by button disable
                }
            }
        }
    }

    private fun showSuccessSection() {
        otpGroup.visibility = View.GONE
        successGroup.visibility = View.VISIBLE
        successEmail.text = preferencesManager.getUserEmail()
    }

    private fun startResendTimer() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                resendHint.text = "Reenviar código en"
                resendTimer.text = "${seconds}s"
            }

            override fun onFinish() {
                resendHint.text = "¿No recibiste el código?"
                resendTimer.text = "Reenviar"
                countDownTimer = null
            }
        }.start()
    }

    private fun getOtpCode(): String {
        return otpFields.joinToString("") { it.text.toString() }
    }

    private fun clearOtpFields() {
        otpFields.forEach { it.text?.clear() }
        verifyButton.isEnabled = false
    }

    private fun showOtpError(message: String) {
        otpFeedbackText.text = message
        otpFeedbackText.setTextColor(resources.getColor(android.R.color.holo_red_light, null))
        otpFeedbackText.visibility = View.VISIBLE
    }

    private fun clearOtpError() {
        otpFeedbackText.visibility = View.GONE
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}