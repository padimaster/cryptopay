package padilla.alex.cryptopay

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityRetiroExitosoBinding

class RetiroExitosoActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRetiroExitosoBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetiroExitosoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        startRetiroAnimation()
    }
    
    private fun setupUI() {
        // Obtener datos del intent
        val amount = intent.getStringExtra("amount") ?: "64.21"
        val destinationWallet = intent.getStringExtra("destinationWallet") ?: "0xc0C91f8584247d3B02F177"
        
        binding.amountText.text = amount
        binding.destinationAddress.text = destinationWallet
        
        // Inicialmente ocultar el contenido de éxito
        binding.successContent.visibility = View.GONE
        binding.buttonsLayout.visibility = View.GONE
    }
    
    private fun startRetiroAnimation() {
        // Mostrar el loader por 3 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            showSuccessContent()
        }, 3000)
    }
    
    private fun showSuccessContent() {
        // Ocultar el loader
        binding.loadingContent.visibility = View.GONE
        
        // Mostrar contenido de éxito con animación
        binding.successContent.visibility = View.VISIBLE
        binding.buttonsLayout.visibility = View.VISIBLE
        
        // Animación de aparición para el ícono de éxito (bounce effect)
        val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_in)
        binding.successIcon.startAnimation(bounceAnimation)
        
        // Animación para el título
        Handler(Looper.getMainLooper()).postDelayed({
            val fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
            binding.successTitle.startAnimation(fadeInAnimation)
        }, 300)
        
        // Animación para el monto
        Handler(Looper.getMainLooper()).postDelayed({
            val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
            binding.amountLayout.startAnimation(slideUpAnimation)
        }, 500)
        
        // Animación para los datos de la transacción
        Handler(Looper.getMainLooper()).postDelayed({
            val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
            binding.transactionDataLayout.startAnimation(slideUpAnimation)
        }, 700)
        
        // Animación para los botones
        Handler(Looper.getMainLooper()).postDelayed({
            val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
            binding.buttonsLayout.startAnimation(slideUpAnimation)
        }, 900)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // Botón atrás
        binding.backButton.setOnClickListener {
            finish()
        }
        
        // Botón copiar hash
        binding.copyHashButton.setOnClickListener {
            copyToClipboard("0xajd871ac......np733v")
        }
        
        // Botón realizar otro retiro
        binding.realizarOtroRetiroButton.setOnClickListener {
            // Volver a la pantalla de retirar crypto
            val intent = Intent(this, RetirarCryptoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        
        // Botón regresar (volver al home)
        binding.regresarButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
    
    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Hash de Transacción", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Hash copiado al portapapeles", Toast.LENGTH_SHORT).show()
    }
}
