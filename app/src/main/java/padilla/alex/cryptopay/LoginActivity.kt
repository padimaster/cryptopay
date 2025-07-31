package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.internal.ViewUtils.hideKeyboard
import padilla.alex.cryptopay.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding
    private var timer: CountDownTimer? = null
    private lateinit var otpBoxes: Array<EditText>

    companion object {
        private const val TAG = "LoginActivity"
        private const val CORRECT_OTP = "123456"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        otpBoxes = arrayOf(b.otp1, b.otp2, b.otp3, b.otp4, b.otp5, b.otp6)

        // CTA inicial
        b.initialLoginButton.setOnClickListener {
            it.visibility = View.GONE
            showLoginPanel()
            b.emailEditText.requestFocus()
            showKeyboard(b.emailEditText)
        }

        b.backButton.setOnClickListener {
            goBackToEmailStep()
        }

        // Validación de email en vivo
        b.continueButton.isEnabled = false
        b.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val ok = Patterns.EMAIL_ADDRESS.matcher(s.toString().trim()).matches()
                b.continueButton.isEnabled = ok
                b.emailInputLayout.error = if (!ok && s?.isNotEmpty() == true) "Correo inválido" else null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Enviar OTP (paso 1 -> paso 2)
        b.continueButton.setOnClickListener {
            val email = b.emailEditText.text.toString().trim()
            if (email.isEmpty()) return@setOnClickListener

            // TODO: llamada a backend para enviar OTP
            b.otpEmail.text = email
            goToOtpStep()
            startResendCountdown()
        }

        // OTP inputs y verificación
        b.verifyButton.setOnClickListener {
            val enteredOtp = otpBoxes.joinToString("") { it.text.toString() }
            hideKeyboard(it)

            if (enteredOtp == CORRECT_OTP) {
                // El código es correcto, muestra la pantalla de éxito
                showSuccessScreen()

                // --- LÓGICA DE REDIRECCIÓN ---
                // Simulación: El backend te diría si el usuario es nuevo.
                // Cambia este valor para probar ambos flujos.
                val isNewUser = true

                // Espera 2 segundos para que el usuario vea la animación
                // y luego redirige.
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isNewUser) {
                        // Redirigir a Onboarding
                        val intent = Intent(this, OnboardingActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Redirigir a la pantalla principal
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    // Cierra LoginActivity para que no se pueda volver.
                    finish()
                }, 2000)

            } else {
                // El código es incorrecto (lógica existente)
                showFeedback("Código incorrecto. Inténtalo de nuevo.", false)
                Handler(Looper.getMainLooper()).postDelayed({
                    resetOtpView()
                }, 2000)
            }
        }

        wireOtpInputs()
    }

    private fun showFeedback(message: String, isSuccess: Boolean) {
        b.otpFeedbackText.apply {
            text = message
            visibility = View.VISIBLE
            setTextColor(
                ContextCompat.getColor(
                    this@LoginActivity,
                    if (isSuccess) R.color.success_500 else R.color.error_500
                )
            )
        }
    }

    private fun showSuccessScreen() {
        // Ocultar todos los elementos del paso OTP
        b.backButton.visibility = View.GONE
        b.otpGroup.visibility = View.GONE

        // Mostrar la vista de éxito
        b.successGroup.visibility = View.VISIBLE
        b.successEmail.text = b.emailEditText.text.toString()
    }


    private fun showLoginPanel() {
        b.loginCard.apply {
            visibility = View.VISIBLE
            alpha = 0f
            translationY = 80f
            post {
                // Asegura que el view ya midió su altura antes de animar
                animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(220L)
                    .withEndAction { Log.d(TAG, "Panel mostrado") }
                    .start()
            }
        }
    }

    // Oculta vistas de Email y muestra vistas de OTP
    private fun goToOtpStep() {
        // Ocultar vistas de Email
        b.loginTitle.visibility = View.GONE
        b.emailInputLayout.visibility = View.GONE
        b.continueButton.visibility = View.GONE

        // Mostrar vistas de OTP
        b.backButton.visibility = View.VISIBLE
        b.otpGroup.visibility = View.VISIBLE // Group simplifica mostrar todo

        b.otp1.requestFocus()
        showKeyboard(b.otp1)
    }

    private fun goBackToEmailStep() {
        // Ocultar vistas de OTP
        b.backButton.visibility = View.GONE
        b.otpGroup.visibility = View.GONE

        // Mostrar vistas de Email
        b.loginTitle.visibility = View.VISIBLE
        b.emailInputLayout.visibility = View.VISIBLE
        b.continueButton.visibility = View.VISIBLE

        b.emailEditText.requestFocus()
        showKeyboard(b.emailEditText)
    }
    private fun startResendCountdown() {
        timer?.cancel()
        b.resendHint.text = "Reenviar código en " // Hint inicial con contador
        b.resendTimer.apply {
            text = "60s"
            isClickable = false
            setOnClickListener(null)
        }

        timer = object : CountDownTimer(60_000, 1_000) {
            override fun onTick(ms: Long) {
                b.resendTimer.text = "${ms / 1000}s"
            }
            override fun onFinish() {
                b.resendHint.text = "¿No has recibido el código?" // Hint final
                b.resendTimer.text = "Reenviar código" // Texto final
                b.resendTimer.isClickable = true
                b.resendTimer.setOnClickListener {
                    // TODO: re-enviar OTP al backend
                    startResendCountdown()
                }
            }
        }.start()
    }

    private fun resetOtpView() {
        otpBoxes.forEach { it.text.clear() }
        b.otpFeedbackText.visibility = View.GONE
        b.verifyButton.visibility = View.GONE
        b.verifyButton.isEnabled = false
        b.otp1.requestFocus()
        showKeyboard(b.otp1)
    }
    private fun wireOtpInputs() {
        for (i in otpBoxes.indices) {
            otpBoxes[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    // Ocultar feedback y botón si el usuario borra texto
                    b.otpFeedbackText.visibility = View.GONE
                    b.verifyButton.visibility = View.GONE
                    b.verifyButton.isEnabled = false

                    if (s?.length == 1) {
                        if (i < otpBoxes.lastIndex) {
                            otpBoxes[i + 1].requestFocus()
                        } else {
                            // Si es el último campo, mostrar el botón "Continuar"
                            b.verifyButton.visibility = View.VISIBLE
                            b.verifyButton.isEnabled = true
                            hideKeyboard(otpBoxes[i])
                        }
                    } else if (s?.isEmpty() == true) {
                        if (i > 0) {
                            otpBoxes[i - 1].requestFocus()
                        }
                    }
                }
            })
        }
    }

    private fun showKeyboard(view: View) {
        view.post {
            try {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            } catch (e: Exception) {
                Log.e(TAG, "No se pudo abrir el teclado: ${e.message}")
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
