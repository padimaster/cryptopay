package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityBilleteraBinding

class BilleteraActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityBilleteraBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBilleteraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // Botón depositar
        binding.depositarButton.setOnClickListener {
            val intent = Intent(this, DepositarActivity::class.java)
            startActivity(intent)
        }
        
        // Botón retirar
        binding.retirarButton.setOnClickListener {
            val intent = Intent(this, RetirarCryptoActivity::class.java)
            startActivity(intent)
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
            // Ya estamos en billetera
        }
    }
}
