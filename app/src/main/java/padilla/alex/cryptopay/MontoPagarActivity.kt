package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MontoPagarActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var amountEditText: EditText
    private lateinit var userName: TextView
    private lateinit var userAccount: TextView
    private lateinit var pagarButton: MaterialButton
    private lateinit var cancelarButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monto_pagar)

        initViews()
        setupClickListeners()
        loadUserData()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        amountEditText = findViewById(R.id.amountEditText)
        userName = findViewById(R.id.userName)
        userAccount = findViewById(R.id.userAccount)
        pagarButton = findViewById(R.id.pagarButton)
        cancelarButton = findViewById(R.id.cancelarButton)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        pagarButton.setOnClickListener {
            processPay()
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

    private fun loadUserData() {
        // Cargar datos del comerciante desde el QR escaneado
        userName.text = "Víveres Don Alfonso"
        userAccount.text = "Cuenta 0xc0C9****np733v"
    }

    private fun processPay() {
        val amountStr = amountEditText.text.toString().trim()

        if (amountStr.isEmpty() || amountStr == "0.00") {
            Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
            return
        }

        // Navegar a confirmar pago
        val intent = Intent(this, ConfirmarPagoActivity::class.java)
        intent.putExtra("amount", amount)
        intent.putExtra("merchant_name", userName.text.toString())
        intent.putExtra("merchant_account", userAccount.text.toString())
        startActivity(intent)
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
