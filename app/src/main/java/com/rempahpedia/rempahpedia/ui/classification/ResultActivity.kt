package com.rempahpedia.rempahpedia.ui.classification

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.rempahpedia.rempahpedia.data.remote.api.ApiConfig
import com.rempahpedia.rempahpedia.data.remote.prediction.PredictionRequest
import com.rempahpedia.rempahpedia.databinding.ActivityResultBinding
import com.rempahpedia.rempahpedia.ui.OnBoardingActivity
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModel
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModelFactory
import com.rempahpedia.rempahpedia.ui.classification.CameraActivity.Companion.CAMERAX_RESULT
import com.rempahpedia.rempahpedia.ui.classification.utils.ImageClassifierHelper
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var binding: ActivityResultBinding
    private var currentImageUri: Uri? = null
    private var currentPrediction: List<String>? = null

    private var token: String? = null
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        authViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            } else {
                token = "access_token=${user.token}"
            }
        }

        startCameraX()

        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        // Save prediction
        currentPrediction?.let { prediction ->
            val label = prediction[0]
            val score = prediction[1].trim('%').toInt()

            if (label == "bukan rempah") {
                return
            } else if (score < 75) {
                Toast.makeText(
                    binding.root.context,
                    "Dapatkan nilai prediksi diatas 75% untuk meng-unlock $label",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                lifecycleScope.launch {
                    val predictionRequest = PredictionRequest(label)
                    ApiConfig.getApiService().savePrediction(token ?: "", predictionRequest)
                }
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.resultImage.setImageURI(it)
            classifySpice(it)
        }
    }

    private fun startCameraX() {
        val intent = Intent(this@ResultActivity, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_CANCELED) {
            finish()
        } else if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun classifySpice(uri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }

                @SuppressLint("SetTextI18n")
                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let {
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                val result = it[0].categories[0]
                                val label = result.label.toString()
                                val score = NumberFormat.getPercentInstance()
                                    .format(result.score).trim()

                                currentPrediction = listOf(label, score)
                                binding.resultText.text = "$label $score"
                            } else {
                                binding.resultText.text = ""
                            }
                        }
                    }
                }
            }
        )

        imageClassifierHelper.classifyStaticImage(uri)
    }
}