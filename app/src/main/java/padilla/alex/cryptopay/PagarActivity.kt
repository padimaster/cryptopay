package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class PagarActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var continueButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagar)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        continueButton = findViewById(R.id.continueButton)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        continueButton.setOnClickListener {
            // Simular escaneo exitoso del QR
            val intent = Intent(this, MontoPagarActivity::class.java)
            startActivity(intent)
        }

        // Configurar navegación del footer
        findViewById<View>(R.id.navHome).setOnClickListener {
            // Navegar a Home
            navigateToHome()
        }

        findViewById<View>(R.id.navCards).setOnClickListener {
            // Navegar a Tarjetas
            navigateToCards()
        }

        findViewById<View>(R.id.navWallet).setOnClickListener {
            // Navegar a Billetera
            navigateToWallet()
        }
    }

    private fun navigateToHome() {
        // Implementar navegación a Home
        Toast.makeText(this, "Navegando a Inicio", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCards() {
        // Implementar navegación a Tarjetas
        Toast.makeText(this, "Navegando a Tarjetas", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToWallet() {
        // Implementar navegación a Billetera
        Toast.makeText(this, "Navegando a Billetera", Toast.LENGTH_SHORT).show()
    }
}