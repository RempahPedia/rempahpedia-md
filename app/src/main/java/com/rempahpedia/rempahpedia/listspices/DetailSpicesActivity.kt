package com.rempahpedia.rempahpedia.listspices

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.data.data.Spices

class DetailSpicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_spices)

        val spice = intent.getParcelableExtra<Spices>("EXTRA_SPICE")

        if (spice != null) {
            val imgItemPhoto = findViewById<ImageView>(R.id.img_item_photo)
            val tvItemName = findViewById<TextView>(R.id.tv_item_name)
            val tvItemDescriptionTitle = findViewById<TextView>(R.id.tv_item_description_title)
            val tvItemDescriptionDetail = findViewById<TextView>(R.id.tv_item_description_detail)

            imgItemPhoto.setImageResource(spice.img)
            tvItemName.text = spice.name
            tvItemDescriptionTitle.text = "Description"
            tvItemDescriptionDetail.text = "This is a description of ${spice.name}"
        }
    }
}
