package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityOnboardingReconocimientoFacialBinding

class OnboardingReconocimientoFacialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingReconocimientoFacialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingReconocimientoFacialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Botón para iniciar reconocimiento facial
        binding.scanButton.setOnClickListener {
            // Por ahora solo simulamos el reconocimiento, más adelante se implementará la cámara
            // Toast.makeText(this, "Iniciando reconocimiento facial...", Toast.LENGTH_SHORT).show()
            // Aquí iría la lógica para abrir la cámara frontal y hacer reconocimiento facial
            
            // Habilitar el botón de continuar después del "escaneo"
            binding.continueButton.isEnabled = true
            binding.continueButton.setBackgroundColor(getColor(R.color.cta))
            binding.continueButton.setTextColor(getColor(R.color.white))
        }

        // Botón de continuar (se habilita después del reconocimiento)
        binding.continueButton.setOnClickListener {
            // Navegar a la pantalla de verificación exitosa
            val intent = Intent(this, VerificacionExitosaActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botón de atrás
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
