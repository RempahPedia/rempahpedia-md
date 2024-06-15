package com.rempahpedia.rempahpedia.ui.jamu

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import com.rempahpedia.rempahpedia.databinding.JamuItemBinding
import java.lang.StringBuilder

class JamuAdapter : ListAdapter<JamuResponseItem, JamuAdapter.ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = JamuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val jamu = getItem(position)
        val binding = holder.binding

        val id = jamu.id
        val nama = jamu.nama
        val listPenyakit = jamu.penyakit

        val penyakitStr = StringBuilder()
        penyakitStr.append("Dapat mengobati:\n")
        for ((i, e) in listPenyakit.withIndex()) {
            penyakitStr.append("${i+1}. $e\n")
        }

        binding.tvJamuName.text = nama
        binding.tvPenyakit.text = penyakitStr

        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "You chose $id/$nama",
                Toast.LENGTH_SHORT
            ).show()

//            val detailIntent = Intent(holder.itemView.context, DetailsActivity::class.java)
//            detailIntent.putExtra(DetailsActivity.USERNAME, username)
//            holder.itemView.context.startActivity(detailIntent)
        }
    }

    class ListViewHolder(var binding: JamuItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<JamuResponseItem>() {
            override fun areItemsTheSame(oldItem: JamuResponseItem, newItem: JamuResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: JamuResponseItem, newItem: JamuResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}