package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityMontoPagarBinding

class MontoPagarActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMontoPagarBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMontoPagarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // Botón volver
        binding.backButton.setOnClickListener {
            finish()
        }
        
        // Botón pagar
        binding.pagarButton.setOnClickListener {
            // Función para procesar el pago
        }
        
        // Botón cancelar
        binding.cancelarButton.setOnClickListener {
            finish()
        }
        
        // Navegación bottom navigation
        binding.navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        
        binding.navCards.setOnClickListener {
            val intent = Intent(this, TarjetasActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        
        binding.navWallet.setOnClickListener {
            // Navegación a billetera (futuro)
        }
        
        binding.navAccounts.setOnClickListener {
            // Navegación a cuentas (futuro)
        }
    }
}
