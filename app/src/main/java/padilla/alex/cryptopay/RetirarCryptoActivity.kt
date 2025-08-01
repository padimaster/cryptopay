package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityRetirarCryptoBinding

class RetirarCryptoActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRetirarCryptoBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetirarCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // Botón volver
        binding.backButton.setOnClickListener {
            finish()
        }
        
        // Botón retirar
        binding.retirarButton.setOnClickListener {
            // Obtener datos del formulario
            val amount = binding.amountInput.text.toString()
            val destinationWallet = binding.destinationWallet.text.toString()
            
            // Navegar a confirmar retiro
            val intent = Intent(this, ConfirmarRetiroActivity::class.java)
            intent.putExtra("amount", amount)
            intent.putExtra("destinationWallet", destinationWallet)
            startActivity(intent)
        }
        
        // Selectores de red y token
        binding.redSelector.setOnClickListener {
            // Lógica para cambiar red
        }
        
        binding.tokenSelector.setOnClickListener {
            // Lógica para cambiar token
        }
    }
}
