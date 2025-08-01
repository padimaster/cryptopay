package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityOnboardingIdentidadBinding

class OnboardingIdentidadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingIdentidadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingIdentidadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Botón para abrir la cámara y escanear
        binding.scanButton.setOnClickListener {
            // Por ahora solo mostramos un toast, más adelante se implementará el escáner
            // Toast.makeText(this, "Función de escáner próximamente", Toast.LENGTH_SHORT).show()
            // Aquí iría la lógica para abrir la cámara y escanear QR
            
            // Habilitar el botón de continuar después del "escaneo"
            binding.continueButton.isEnabled = true
            binding.continueButton.setBackgroundColor(getColor(R.color.cta))
            binding.continueButton.setTextColor(getColor(R.color.white))
        }

        // Botón de continuar (se puede habilitar después del escaneo)
        binding.continueButton.setOnClickListener {
            // Navegar a la pantalla de reconocimiento facial
            val intent = Intent(this, OnboardingReconocimientoFacialActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botón de atrás
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
