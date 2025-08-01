package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityMovimientosBinding

class MovimientosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovimientosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovimientosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        // Navegación inferior
        binding.navHome.setOnClickListener {
            finish() // Volver al Home
        }

        binding.navCards.setOnClickListener {
            // TODO: Implementar navegación a Tarjetas
        }

        binding.navWallet.setOnClickListener {
            val intent = Intent(this, BilleteraActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}
