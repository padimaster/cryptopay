package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityVerificacionExitosaBinding

class VerificacionExitosaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificacionExitosaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificacionExitosaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Botón de continuar
        binding.continueButton.setOnClickListener {
            // Navegar a la pantalla principal de la app
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
