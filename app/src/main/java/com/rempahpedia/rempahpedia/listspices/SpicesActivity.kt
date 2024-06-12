package com.rempahpedia.rempahpedia.listspices

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.data.data.Spices

class SpicesActivity : AppCompatActivity() {

    private lateinit var rvSpices: RecyclerView
    private val list = ArrayList<Spices>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spices)
        rvSpices = findViewById(R.id.rv_spices)
        rvSpices.setHasFixedSize(true)

        list.addAll(getListSpices())
        showRecyclerList()
    }

    private fun getListSpices(): ArrayList<Spices> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listSpices = ArrayList<Spices>()
        for (i in dataName.indices) {
            val spices = Spices(dataName[i], dataPhoto.getResourceId(i, -1))
            listSpices.add(spices)
        }
        dataPhoto.recycle()
        return listSpices
    }

    private fun showRecyclerList() {
        rvSpices.layoutManager = LinearLayoutManager(this)
        val listSpicesAdapter = ListSpicesAdapter(list)
        rvSpices.adapter = listSpicesAdapter
    }
}
