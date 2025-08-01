package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityPagoExitosoBinding

class PagoExitosoActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPagoExitosoBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagoExitosoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        startPaymentAnimation()
    }
    
    private fun setupUI() {
        // Obtener datos del intent
        val amount = intent.getStringExtra("amount") ?: "64.21"
        binding.amountText.text = amount
        
        // Inicialmente ocultar el contenido de éxito
        binding.successContent.visibility = View.GONE
        binding.buttonsLayout.visibility = View.GONE
    }
    
    private fun startPaymentAnimation() {
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
        
        // Animación para los datos del usuario
        Handler(Looper.getMainLooper()).postDelayed({
            val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
            binding.userDataLayout.startAnimation(slideUpAnimation)
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
        
        // Botón realizar otro pago
        binding.realizarOtroPagoButton.setOnClickListener {
            // Volver a la pantalla de pagar (escaner QR)
            val intent = Intent(this, PagarActivity::class.java)
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
