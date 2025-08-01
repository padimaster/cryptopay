package padilla.alex.cryptopay

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        // Agregar subrayado al texto "Ver más"
        binding.seeMoreTextView.paintFlags = binding.seeMoreTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun setupClickListeners() {
        // Botones principales
        binding.depositButton.setOnClickListener {
            val intent = Intent(this, DepositarActivity::class.java)
            startActivity(intent)
        }

        binding.payButton.setOnClickListener {
            val intent = Intent(this, PagarActivity::class.java)
            startActivity(intent)
        }

        // Cards de cuentas
        binding.walletCard.setOnClickListener {
            Toast.makeText(this, "Billetera próximamente", Toast.LENGTH_SHORT).show()
        }

        binding.usdAccountCard.setOnClickListener {
            Toast.makeText(this, "Cuenta USD próximamente", Toast.LENGTH_SHORT).show()
        }

        // Ver más movimientos
        binding.seeMoreTextView.setOnClickListener {
            val intent = Intent(this, MovimientosActivity::class.java)
            startActivity(intent)
        }

        // Navegación inferior
        binding.navHome.setOnClickListener {
            // Ya estamos en Home
        }

        binding.navCards.setOnClickListener {
            val intent = Intent(this, TarjetasActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.navWallet.setOnClickListener {
            val intent = Intent(this, BilleteraActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}
