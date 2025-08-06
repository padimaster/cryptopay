package padilla.alex.cryptopay

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DepositarActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var walletAddressText: TextView
    private lateinit var copyButton: ImageButton

    private val walletAddress = "0xc0C91f8584247dc9f0C6309Ca1np733v"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depositar)

        initViews()
        setupClickListeners()
        updateWalletAddress()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        walletAddressText = findViewById(R.id.walletAddressText)
        copyButton = findViewById(R.id.copyButton)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        copyButton.setOnClickListener {
            copyWalletAddress()
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

    private fun updateWalletAddress() {
        // Mostrar dirección abreviada
        val shortAddress = "${walletAddress.substring(0, 6)}...${walletAddress.substring(walletAddress.length - 6)}"
        walletAddressText.text = shortAddress
    }

    private fun copyWalletAddress() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Wallet Address", walletAddress)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(this, "Dirección copiada al portapapeles", Toast.LENGTH_SHORT).show()
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