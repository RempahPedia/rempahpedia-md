package com.rempahpedia.rempahpedia.ui.classification

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.rempahpedia.rempahpedia.databinding.ActivityResultBinding
import com.rempahpedia.rempahpedia.ui.classification.CameraActivity.Companion.CAMERAX_RESULT
import com.rempahpedia.rempahpedia.ui.classification.utils.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var binding: ActivityResultBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        startCameraX()

        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.resultImage.setImageURI(it)
            classifyCancer(it)
        }
    }

    private fun startCameraX() {
        val intent = Intent(this@ResultActivity, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun classifyCancer(uri: Uri) {
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