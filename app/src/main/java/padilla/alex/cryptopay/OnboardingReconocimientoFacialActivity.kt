package padilla.alex.cryptopay

import android.Manifest
import android.R.attr.visibility
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.graphics.*
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

class OnboardingReconocimientoFacialActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "FaceRecognitionOnboarding"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    // UI Components
    private lateinit var backButton: ImageButton
    private lateinit var previewView: PreviewView
    private lateinit var instructionsText: TextView
    private lateinit var captureButton: MaterialButton
    private lateinit var retryButton: MaterialButton
    private lateinit var continueButton: MaterialButton

    // Camera Components
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var imageAnalyzer: ImageAnalysis
    private lateinit var cameraExecutor: ExecutorService
    private var cameraProvider: ProcessCameraProvider? = null
    private var preview: Preview? = null
    private var imageAnalysis: ImageAnalysis? = null
    private var camera: Camera? = null

    // Face Recognition State
    private var isFaceDetected = false
    private var isCapturing = false
    private var captureAttempts = 0
    private val maxAttempts = 3
    private var currentStep = FaceRecognitionStep.DETECTING

    private enum class FaceRecognitionStep {
        DETECTING,
        CAPTURED,
        VERIFIED,
        ERROR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_reconocimiento_facial)

        initViews()
        setupClickListeners()

        // Request camera permissions
        if (allPermissionsGranted()) {
            setupCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        previewView = findViewById(R.id.previewView)
        instructionsText = findViewById(R.id.instructionsText)
        captureButton = findViewById(R.id.captureButton)
        retryButton = findViewById(R.id.retryButton)
        continueButton = findViewById(R.id.continueButton)

        // Initialize camera executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Set initial UI state
        updateUIForStep(FaceRecognitionStep.DETECTING)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        captureButton.setOnClickListener {
            captureFace()
        }

        retryButton.setOnClickListener {
            retryCapture()
        }

        continueButton.setOnClickListener {
            proceedToNextStep()
        }
    }

    private fun setupCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                bindCameraUseCases()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to get camera provider", e)
                showError("Error al inicializar la cámara")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraUseCases() {
        val cameraProvider = cameraProvider ?: throw IllegalStateException("Camera initialization failed.")

        // Preview use case
        preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        // Image analysis use case for face detection
        imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(android.util.Size(640, 480))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, FaceAnalyzer())
            }

        // Select front camera as default
        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        try {
            // Unbind use cases before rebinding
            cameraProvider.unbindAll()

            // Bind use cases to camera
            camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageAnalysis
            )

        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
            showError("Error al configurar la cámara")
        }
    }

    private inner class FaceAnalyzer : ImageAnalysis.Analyzer {
        override fun analyze(imageProxy: ImageProxy) {
            if (currentStep != FaceRecognitionStep.DETECTING || isCapturing) {
                imageProxy.close()
                return
            }

            // Simple face detection simulation
            // In a real implementation, you would use ML Kit Face Detection or similar
            val faceDetected = simulateFaceDetection(imageProxy)

            runOnUiThread {
                if (faceDetected != isFaceDetected) {
                    isFaceDetected = faceDetected
                    updateFaceDetectionUI()
                }
            }

            imageProxy.close()
        }
    }

    private fun simulateFaceDetection(imageProxy: ImageProxy): Boolean {
        // Simulate face detection logic
        // In a real implementation, you would analyze the image for faces
        // For now, we'll simulate detection after a few seconds
        return System.currentTimeMillis() % 3000 < 1500
    }

    private fun updateFaceDetectionUI() {
        if (isFaceDetected && currentStep == FaceRecognitionStep.DETECTING) {
            instructionsText.text = "Rostro detectado. Mantente quieto y presiona capturar."
            instructionsText.setTextColor(ContextCompat.getColor(this, R.color.success))
            captureButton.isEnabled = true
        } else if (currentStep == FaceRecognitionStep.DETECTING) {
            instructionsText.text = "Posiciona tu rostro en el centro de la cámara"
            instructionsText.setTextColor(ContextCompat.getColor(this, R.color.white))
            captureButton.isEnabled = false
        }
    }

    private fun captureFace() {
        if (!isFaceDetected || isCapturing) {
            return
        }

        isCapturing = true
        captureAttempts++

        captureButton.isEnabled = false
        captureButton.text = "Capturando..."

        // Simulate face capture and verification
        android.os.Handler().postDelayed({
            val captureSuccess = simulateFaceCapture()

            if (captureSuccess) {
                currentStep = FaceRecognitionStep.CAPTURED
                verifyFace()
            } else {
                handleCaptureError()
            }
        }, 2000)
    }

    private fun simulateFaceCapture(): Boolean {
        // Simulate face capture success/failure
        return captureAttempts <= 2 || Math.random() > 0.3
    }

    private fun verifyFace() {
        updateUIForStep(FaceRecognitionStep.CAPTURED)

        // Simulate face verification process
        android.os.Handler().postDelayed({
            val verificationSuccess = simulateFaceVerification()

            if (verificationSuccess) {
                currentStep = FaceRecognitionStep.VERIFIED
                updateUIForStep(FaceRecognitionStep.VERIFIED)
            } else {
                handleVerificationError()
            }
        }, 3000)
    }

    private fun simulateFaceVerification(): Boolean {
        // Simulate face verification success/failure
        return Math.random() > 0.2 // 80% success rate
    }

    private fun handleCaptureError() {
        isCapturing = false

        if (captureAttempts >= maxAttempts) {
            currentStep = FaceRecognitionStep.ERROR
            updateUIForStep(FaceRecognitionStep.ERROR)
        } else {
            instructionsText.text = "Error en la captura. Intenta nuevamente."
            instructionsText.setTextColor(ContextCompat.getColor(this, R.color.error))
            captureButton.isEnabled = true
            captureButton.text = "Capturar"
        }
    }

    private fun handleVerificationError() {
        isCapturing = false

        if (captureAttempts >= maxAttempts) {
            currentStep = FaceRecognitionStep.ERROR
            updateUIForStep(FaceRecognitionStep.ERROR)
        } else {
            instructionsText.text = "Verificación fallida. Intenta nuevamente."
            instructionsText.setTextColor(ContextCompat.getColor(this, R.color.error))
            updateUIForStep(FaceRecognitionStep.DETECTING)
        }
    }

    private fun updateUIForStep(step: FaceRecognitionStep) {
        when (step) {
            FaceRecognitionStep.DETECTING -> {
                instructionsText.text = "Posiciona tu rostro en el centro de la cámara"
                instructionsText.setTextColor(ContextCompat.getColor(this, R.color.white))
                captureButton.isVisible = true
                captureButton.isEnabled = false
                captureButton.text = "Capturar"
                retryButton.isVisible = false
                continueButton.isVisible = false
            }

            FaceRecognitionStep.CAPTURED -> {
                instructionsText.text = "Verificando identidad..."
                instructionsText.setTextColor(ContextCompat.getColor(this, R.color.accent_500))
                captureButton.isVisible = false
                retryButton.isVisible = false
                continueButton.isVisible = false
            }

            FaceRecognitionStep.VERIFIED -> {
                instructionsText.text = "¡Verificación exitosa! Tu identidad ha sido confirmada."
                instructionsText.setTextColor(ContextCompat.getColor(this, R.color.success))
                captureButton.isVisible = false
                retryButton.isVisible = false
                continueButton.isVisible = true
                continueButton.isEnabled = true
            }

            FaceRecognitionStep.ERROR -> {
                instructionsText.text = "No se pudo verificar tu identidad. Intenta más tarde o contacta soporte."
                instructionsText.setTextColor(ContextCompat.getColor(this, R.color.error))
                captureButton.isVisible = false
                retryButton.isVisible = true
                continueButton.isVisible = false
            }
        }
    }

    private fun retryCapture() {
        captureAttempts = 0
        currentStep = FaceRecognitionStep.DETECTING
        isCapturing = false
        updateUIForStep(FaceRecognitionStep.DETECTING)
    }

    private fun proceedToNextStep() {
        // Save facial recognition completion status
        val sharedPrefs = getSharedPreferences("crypto_wallet_prefs", MODE_PRIVATE)
        sharedPrefs.edit().putBoolean("facial_recognition_completed", true).apply()

        // Navigate to next onboarding step or main app
        val intent = Intent(this, MainActivity::class.java) // or next onboarding activity
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        currentStep = FaceRecognitionStep.ERROR
        updateUIForStep(FaceRecognitionStep.ERROR)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                setupCamera()
            } else {
                Toast.makeText(
                    this,
                    "Se requieren permisos de cámara para continuar.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        cameraProvider?.unbindAll()
    }

    override fun onPause() {
        super.onPause()
        // Pause camera operations when activity is not visible
        cameraProvider?.unbindAll()
    }

    override fun onResume() {
        super.onResume()
        // Resume camera operations when activity becomes visible
        if (allPermissionsGranted() && cameraProvider != null) {
            bindCameraUseCases()
        }
    }

    // Extension function to make view visibility easier
    private var androidx.core.view.isVisible: Boolean
        get() = visibility == android.view.View.VISIBLE
        set(value) {
            visibility = if (value) android.view.View.VISIBLE else android.view.View.GONE
        }
}

// Additional data class for face recognition results (if needed)
data class FaceRecognitionResult(
    val isSuccess: Boolean,
    val confidence: Float,
    val timestamp: Long,
    val faceId: String? = null,
    val errorMessage: String? = null
)