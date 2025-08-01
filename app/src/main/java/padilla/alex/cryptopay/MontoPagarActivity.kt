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
        
        setupUI()
        setupClickListeners()
    }
    
    private fun setupUI() {
        // Configurar el EditText para que se enfoque automáticamente
        binding.amountEditText.requestFocus()
        
        // Seleccionar todo el texto cuando se enfoque
        binding.amountEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.amountEditText.selectAll()
            }
        }
    }
    
    private fun setupClickListeners() {
        // Botón atrás
        binding.backButton.setOnClickListener {
            finish()
        }
        
        // Botón pagar
        binding.pagarButton.setOnClickListener {
            // Obtener el monto ingresado
            val amount = binding.amountEditText.text.toString()
            
            // Navegar a confirmar pago
            val intent = Intent(this, ConfirmarPagoActivity::class.java)
            intent.putExtra("amount", amount)
            startActivity(intent)
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
