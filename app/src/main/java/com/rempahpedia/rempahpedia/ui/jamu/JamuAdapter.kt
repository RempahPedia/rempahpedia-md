package com.rempahpedia.rempahpedia.ui.jamu

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import com.rempahpedia.rempahpedia.databinding.JamuItemBinding

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
        for ((i, e) in listPenyakit.withIndex()) {
            penyakitStr.append("${i + 1}. $e\n")
        }

        binding.tvJamuName.text = nama
        binding.tvMengobati.text = holder.itemView.context.getString(R.string.mengobati)
        binding.tvPenyakit.text = penyakitStr

        holder.itemView.setOnClickListener {
            val detailIntent = Intent(holder.itemView.context, JamuDetailActivity::class.java)
            detailIntent.putExtra(JamuDetailActivity.ID, id)
            holder.itemView.context.startActivity(detailIntent)
        }
    }

    class ListViewHolder(var binding: JamuItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<JamuResponseItem>() {
            override fun areItemsTheSame(
                oldItem: JamuResponseItem,
                newItem: JamuResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: JamuResponseItem,
                newItem: JamuResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}