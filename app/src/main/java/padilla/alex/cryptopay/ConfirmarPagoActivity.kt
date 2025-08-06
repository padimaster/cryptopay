package padilla.alex.cryptopay

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ConfirmarPagoActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var amountText: TextView
    private lateinit var userName: TextView
    private lateinit var userAccount: TextView
    private lateinit var pagarButton: MaterialButton
    private lateinit var cancelarButton: MaterialButton

    private var paymentAmount: Double = 0.0
    private var merchantName: String = ""
    private var merchantAccount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_pago)

        initViews()
        getIntentData()
        setupClickListeners()
        loadPaymentData()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        amountText = findViewById(R.id.amountText)
        userName = findViewById(R.id.userName)
        userAccount = findViewById(R.id.userAccount)
        pagarButton = findViewById(R.id.pagarButton)
        cancelarButton = findViewById(R.id.cancelarButton)
    }

    private fun getIntentData() {
        paymentAmount = intent.getDoubleExtra("amount", 0.0)
        merchantName = intent.getStringExtra("merchant_name") ?: "Comerciante"
        merchantAccount = intent.getStringExtra("merchant_account") ?: "Cuenta"
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        pagarButton.setOnClickListener {
            confirmPayment()
        }

        cancelarButton.setOnClickListener {
            cancelPayment()
        }

        // Configurar navegación del footer
        findViewById<View>(R.id.navHome).setOnClickListener {
            navigateToHome()
        }

        findViewById<View>(R.id.navCards).setOnClickListener {
            navigateToCards()
        }

        findViewById<View>(R.id.navWallet).setOnClickListener {
            navigateToWallet()
        }
    }

    private fun loadPaymentData() {
        amountText.text = String.format("%.2f", paymentAmount)
        userName.text = merchantName
        userAccount.text = merchantAccount
    }

    private fun confirmPayment() {
        // Aquí iría la lógica real del pago con blockchain
        Toast.makeText(this, "Procesando pago...", Toast.LENGTH_SHORT).show()

        // Simular procesamiento
        pagarButton.isEnabled = false
        pagarButton.text = "Procesando..."

        // En una implementación real, aquí harías la transacción blockchain
        android.os.Handler().postDelayed({
            Toast.makeText(this, "¡Pago realizado exitosamente!", Toast.LENGTH_LONG).show()
            finish()
        }, 2000)
    }

    private fun cancelPayment() {
        finish()
    }

    private fun navigateToHome() {
        Toast.makeText(this, "Navegando a Inicio", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCards() {
        Toast.makeText(this, "Navegando a Tarjetas", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToWallet() {
        Toast.makeText(this, "Navegando a Billetera", Toast.LENGTH_SHORT).show()
    }
}