package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import padilla.alex.cryptopay.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar los menús desplegables
        setupDropdowns()

        // Configurar los listeners para validar el formulario
        setupFormValidation()

        // Configurar el click del botón
        binding.continueButton.setOnClickListener {
            // Aquí iría la lógica para guardar los datos del usuario en el backend
            Toast.makeText(this, "Perfil guardado. ¡Bienvenido!", Toast.LENGTH_SHORT).show()

            // Navegar a la pantalla principal
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cierra esta actividad para que no se pueda volver
        }
    }

    private fun setupDropdowns() {
        // Lista de países (puedes obtenerla de tus recursos o una API)
        val countries = listOf("Brasil", "Argentina", "Chile", "Colombia", "México", "Perú", "Venezuela")
        val countryAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        binding.countryAutoComplete.setAdapter(countryAdapter)

        // Lista de tipos de identificación
        val idTypes = listOf("Documento de Identidad", "Pasaporte", "Licencia de Conducir")
        val idTypeAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, idTypes)
        binding.idTypeAutoComplete.setAdapter(idTypeAdapter)
    }

    private fun setupFormValidation() {
        val fields = listOf(
            binding.nameEditText,
            binding.lastNameEditText,
            binding.countryAutoComplete,
            binding.idTypeAutoComplete
        )

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validateForm()
            }
        }

        fields.forEach { it.addTextChangedListener(textWatcher) }
    }

    private fun validateForm() {
        val isNameValid = binding.nameEditText.text?.isNotEmpty() == true
        val isLastNameValid = binding.lastNameEditText.text?.isNotEmpty() == true
        val isCountryValid = binding.countryAutoComplete.text?.isNotEmpty() == true
        val isIdTypeValid = binding.idTypeAutoComplete.text?.isNotEmpty() == true

        // Habilita el botón solo si todos los campos son válidos
        binding.continueButton.isEnabled = isNameValid && isLastNameValid && isCountryValid && isIdTypeValid
    }
}