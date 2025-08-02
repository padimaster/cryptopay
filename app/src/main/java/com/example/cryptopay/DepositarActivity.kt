package padilla.alex.cryptopay

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityDepositarBinding

class DepositarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDepositarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepositarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupNavigationListeners()
    }

    private fun setupUI() {
        // Configurar dirección de wallet por defecto
        binding.walletAddressText.text = "0xc0C9.......np733v"
    }

    private fun setupNavigationListeners() {
        // Botón de atrás
        binding.backButton.setOnClickListener {
            finish()
        }

        // Botón de copiar dirección
        binding.copyButton.setOnClickListener {
            copyWalletAddress()
        }

        // Navegación inferior
        binding.navHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.navCards.setOnClickListener {
            startActivity(Intent(this, TarjetasActivity::class.java))
            finish()
        }

        binding.navWallet.setOnClickListener {
            startActivity(Intent(this, BilleteraActivity::class.java))
            finish()
        }

        binding.navAccounts.setOnClickListener {
            startActivity(Intent(this, CuentasActivity::class.java))
            finish()
        }
    }

    private fun copyWalletAddress() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Wallet Address", "0xc0C9.......np733v")
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Dirección copiada al portapapeles", Toast.LENGTH_SHORT).show()
    }
}
