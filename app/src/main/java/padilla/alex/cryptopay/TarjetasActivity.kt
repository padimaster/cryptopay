package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityTarjetasBinding

class TarjetasActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityTarjetasBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarjetasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Navegación bottom navigation
        binding.navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        
        binding.navCards.setOnClickListener {
            // Ya estamos en tarjetas, no hacer nada
        }
        
        binding.navWallet.setOnClickListener {
            // Navegación a billetera (futuro)
        }
        
        binding.navAccounts.setOnClickListener {
            // Navegación a cuentas (futuro)
        }
    }
}
