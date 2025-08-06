package padilla.alex.cryptopay

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RetirarCryptoActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var redSelector: LinearLayout
    private lateinit var tokenSelector: LinearLayout
    private lateinit var destinationWallet: EditText
    private lateinit var amountInput: EditText
    private lateinit var retirarButton: MaterialButton

    private var selectedNetwork = "Polygon"
    private var selectedToken = "USDT"
    private val availableBalance = 605.13

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retirar_crypto)

        initViews()
        setupClickListeners()
        updateUI()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        redSelector = findViewById(R.id.redSelector)
        tokenSelector = findViewById(R.id.tokenSelector)
        destinationWallet = findViewById(R.id.destinationWallet)
        amountInput = findViewById(R.id.amountInput)
        retirarButton = findViewById(R.id.retirarButton)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        redSelector.setOnClickListener {
            showNetworkSelector()
        }

        tokenSelector.setOnClickListener {
            showTokenSelector()
        }

        retirarButton.setOnClickListener {
            processWithdrawal()
        }

        // Listener para actualizar el total cuando cambie el monto
        amountInput.setOnFocusChangeListener { _, _ ->
            updateTotal()
        }
    }

    private fun updateUI() {
        // Actualizar la interfaz con los valores actuales
        updateTotal()
    }

    private fun updateTotal() {
        try {
            val amount = amountInput.text.toString().toDoubleOrNull() ?: 0.0
            val fee = 0.05 // Fee fijo de ejemplo
            val total = amount + fee

            // Actualizar el TextView del total (necesitarías agregarlo al layout)
            // totalTextView.text = "Total: ${String.format("%.2f", total)} $selectedToken"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showNetworkSelector() {
        // Implementar selector de red
        Toast.makeText(this, "Seleccionar red", Toast.LENGTH_SHORT).show()
    }

    private fun showTokenSelector() {
        // Implementar selector de token
        Toast.makeText(this, "Seleccionar token", Toast.LENGTH_SHORT).show()
    }

    private fun processWithdrawal() {
        val destinationAddress = destinationWallet.text.toString().trim()
        val amountStr = amountInput.text.toString().trim()

        if (destinationAddress.isEmpty()) {
            Toast.makeText(this, "Ingrese la dirección de destino", Toast.LENGTH_SHORT).show()
            return
        }

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Ingrese el monto a retirar", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
            return
        }

        if (amount > availableBalance) {
            Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
            return
        }

        // Procesar retiro
        Toast.makeText(this, "Procesando retiro...", Toast.LENGTH_SHORT).show()
        // Aquí iría la lógica real del retiro
    }
}