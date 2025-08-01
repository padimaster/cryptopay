package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityConfirmarPagoBinding

class ConfirmarPagoActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityConfirmarPagoBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmarPagoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupClickListeners()
    }
    
    private fun setupUI() {
        // Obtener datos del intent
        val amount = intent.getStringExtra("amount") ?: "64.21"
        binding.amountText.text = amount
    }
    
    private fun setupClickListeners() {
        // Botón atrás
        binding.backButton.setOnClickListener {
            finish()
        }
        
        // Botón pagar
        binding.pagarButton.setOnClickListener {
            // Obtener el monto
            val amount = intent.getStringExtra("amount") ?: "64.21"
            
            // Navegar a pago exitoso
            val intent = Intent(this, PagoExitosoActivity::class.java)
            intent.putExtra("amount", amount)
            startActivity(intent)
            finish()
        }
        
        // Botón cancelar
        binding.cancelarButton.setOnClickListener {
            // Volver al home o cerrar
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
            val intent = Intent(this, BilleteraActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}
