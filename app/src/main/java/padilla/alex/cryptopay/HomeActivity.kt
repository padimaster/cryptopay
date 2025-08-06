package padilla.alex.cryptopay

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import padilla.alex.cryptopay.repository.ApiResult
import padilla.alex.cryptopay.repository.CryptoPayRepository
import padilla.alex.cryptopay.utils.PreferencesManager
import padilla.alex.cryptopay.utils.Utils

class HomeActivity : AppCompatActivity() {

    // UI Elements - usando exactamente los IDs de tu layout
    private lateinit var logoImageView: ImageView
    private lateinit var balanceLabelTextView: TextView
    private lateinit var balanceAmountTextView: TextView
    private lateinit var depositButton: MaterialButton
    private lateinit var payButton: MaterialButton
    private lateinit var walletCard: CardView
    private lateinit var usdAccountCard: CardView
    private lateinit var walletTitleTextView: TextView
    private lateinit var walletSubtitleTextView: TextView
    private lateinit var usdAccountTitleTextView: TextView
    private lateinit var usdAccountSubtitleTextView: TextView
    private lateinit var seeMoreTextView: TextView
    private lateinit var transactionsLayout: LinearLayout

    // Bottom Navigation
    private lateinit var navHome: LinearLayout
    private lateinit var navCards: LinearLayout
    private lateinit var navWallet: LinearLayout

    // Repository and Utils
    private val repository = CryptoPayRepository()
    private lateinit var preferencesManager: PreferencesManager

    // State
    private var currentBalance: Double = 0.0
    private var currentCurrency: String = "USD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupListeners()
        preferencesManager = PreferencesManager(this)

        // Verificar autenticación
        if (!preferencesManager.isLoggedIn()) {
            redirectToLogin()
            return
        }

        // Cargar datos
        loadBalance()
        setupTransactionsList()
    }

    override fun onResume() {
        super.onResume()
        // Refrescar balance cuando regrese a la actividad
        if (preferencesManager.isLoggedIn()) {
            loadBalance()
        }
    }

    private fun initViews() {
        logoImageView = findViewById(R.id.logoImageView)
        balanceLabelTextView = findViewById(R.id.balanceLabelTextView)
        balanceAmountTextView = findViewById(R.id.balanceAmountTextView)
        depositButton = findViewById(R.id.depositButton)
        payButton = findViewById(R.id.payButton)
        walletCard = findViewById(R.id.walletCard)
        usdAccountCard = findViewById(R.id.usdAccountCard)
        walletTitleTextView = findViewById(R.id.walletTitleTextView)
        walletSubtitleTextView = findViewById(R.id.walletSubtitleTextView)
        usdAccountTitleTextView = findViewById(R.id.usdAccountTitleTextView)
        usdAccountSubtitleTextView = findViewById(R.id.usdAccountSubtitleTextView)
        seeMoreTextView = findViewById(R.id.seeMoreTextView)
        transactionsLayout = findViewById(R.id.transactionsLayout)

        // Bottom navigation
        navHome = findViewById(R.id.navHome)
        navCards = findViewById(R.id.navCards)
        navWallet = findViewById(R.id.navWallet)
    }

    private fun setupListeners() {
        // Action buttons
        depositButton.setOnClickListener {
            startActivity(Intent(this, DepositarActivity::class.java))
        }

        payButton.setOnClickListener {
            startActivity(Intent(this, PagarActivity::class.java))
        }

        // Account cards
        walletCard.setOnClickListener {
            startActivity(Intent(this, BilleteraActivity::class.java))
        }

        usdAccountCard.setOnClickListener {
            // Navigate to USD account details
            Utils.showToast(this, "Función de cuenta USD próximamente")
        }

        // See more movements
        seeMoreTextView.setOnClickListener {
            startActivity(Intent(this, MovimientosActivity::class.java))
        }

        // Bottom navigation
        navHome.setOnClickListener {
            // Already in home, do nothing
        }

        navCards.setOnClickListener {
            startActivity(Intent(this, TarjetasActivity::class.java))
        }

        navWallet.setOnClickListener {
            startActivity(Intent(this, BilleteraActivity::class.java))
        }

        // Pull to refresh gesture (optional)
        balanceAmountTextView.setOnClickListener {
            loadBalance()
        }
    }

    private fun loadBalance() {
        val token = preferencesManager.getAuthToken()
        if (token == null) {
            Utils.showToast(this, "Error: Token no encontrado")
            redirectToLogin()
            return
        }

        // Show loading state
        showLoadingState(true)

        lifecycleScope.launch {
            when (val result = repository.getBalance(token)) {
                is ApiResult.Success -> {
                    showLoadingState(false)
                    currentBalance = result.data.balance
                    currentCurrency = result.data.currency
                    updateBalanceUI()
                    updateWalletInfo()
                }
                is ApiResult.Error -> {
                    showLoadingState(false)
                    Utils.showToast(this@HomeActivity, "Error al cargar balance: ${result.message}")

                    // Si es error de autenticación, redirigir al login
                    if (result.message.contains("401") ||
                        result.message.contains("Unauthorized") ||
                        result.message.contains("Token")) {
                        redirectToLogin()
                    }
                }
                is ApiResult.Loading -> {
                    // Already showing loading
                }
            }
        }
    }

    private fun updateBalanceUI() {
        val formattedBalance = when (currentCurrency) {
            "USD" -> "$${Utils.formatBalance(currentBalance)}"
            "ETH" -> "${Utils.formatBalance(currentBalance)} ETH"
            else -> "${Utils.formatBalance(currentBalance)} $currentCurrency"
        }

        balanceAmountTextView.text = formattedBalance
    }

    private fun updateWalletInfo() {
        // Update wallet subtitle with current balance info
        walletSubtitleTextView.text = "${Utils.formatBalance(currentBalance)} $currentCurrency"

        // You could also update USD account info here if available
        // usdAccountSubtitleTextView.text = "Balance disponible"
    }

    private fun setupTransactionsList() {
        // The transactions are already set in the layout as static content
        // You could make this dynamic by clearing transactionsLayout and adding views programmatically
        // For now, we'll keep the static content from the layout

        // If you want to load real transactions:
        // loadRecentTransactions()
    }

    private fun loadRecentTransactions() {
        // TODO: Implement API call to get recent transactions
        // This would be a new endpoint in your API

        // Example implementation:
        /*
        lifecycleScope.launch {
            when (val result = repository.getRecentTransactions(token)) {
                is ApiResult.Success -> {
                    updateTransactionsList(result.data)
                }
                is ApiResult.Error -> {
                    // Handle error silently or show message
                }
                is ApiResult.Loading -> {}
            }
        }
        */
    }

    private fun updateTransactionsList(transactions: List<Any>) {
        // Clear existing transactions
        transactionsLayout.removeAllViews()

        // Add new transactions dynamically
        // Implementation depends on your Transaction data model
    }

    private fun showLoadingState(show: Boolean) {
        if (show) {
            balanceAmountTextView.text = "Cargando..."
            balanceAmountTextView.alpha = 0.7f
        } else {
            balanceAmountTextView.alpha = 1.0f
        }

        // Disable buttons while loading
        depositButton.isEnabled = !show
        payButton.isEnabled = !show
    }

    private fun redirectToLogin() {
        preferencesManager.clearAllData()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    // Optional: Handle back press
    override fun onBackPressed() {
        super.onBackPressed()
        // Show confirmation dialog or minimize app
        // For now, just minimize
        moveTaskToBack(true)
    }
}