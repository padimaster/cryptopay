package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityConfirmarRetiroBinding

class ConfirmarRetiroActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityConfirmarRetiroBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmarRetiroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupClickListeners()
    }
    
    private fun setupUI() {
        // Obtener datos del intent
        val amount = intent.getStringExtra("amount") ?: "64.21"
        val destinationWallet = intent.getStringExtra("destinationWallet") ?: "0xc0C91f8584247dc9f0C6309Ca1"
        
        binding.amountText.text = amount
        binding.destinationAddress.text = destinationWallet
    }
    
    private fun setupClickListeners() {
        // Botón atrás
        binding.backButton.setOnClickListener {
            finish()
        }
        
        // Botón retirar
        binding.retirarButton.setOnClickListener {
            // Obtener datos
            val amount = intent.getStringExtra("amount") ?: "64.21"
            val destinationWallet = intent.getStringExtra("destinationWallet") ?: "0xc0C91f8584247d3B02F177"
            
            // Navegar a retiro exitoso
            val intent = Intent(this, RetiroExitosoActivity::class.java)
            intent.putExtra("amount", amount)
            intent.putExtra("destinationWallet", destinationWallet)
            startActivity(intent)
            finish()
        }
        
        // Botón cancelar
        binding.cancelarButton.setOnClickListener {
            finish()
        }
    }
}
