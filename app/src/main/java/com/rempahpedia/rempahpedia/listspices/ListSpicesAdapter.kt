package com.rempahpedia.rempahpedia.listspices

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.data.data.Spices

class ListSpicesAdapter(private val listSpices: ArrayList<Spices>) : RecyclerView.Adapter<ListSpicesAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_spices, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listSpices.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, img) = listSpices[position]
        holder.imgSpices.setImageResource(img)
        holder.nameSpices.text = name

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailSpicesActivity::class.java)
            intent.putExtra("EXTRA_SPICE", listSpices[position])
            context.startActivity(intent)
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgSpices: ImageView = itemView.findViewById(R.id.spices_img)
        val nameSpices: TextView = itemView.findViewById(R.id.spices_name)
    }
}

