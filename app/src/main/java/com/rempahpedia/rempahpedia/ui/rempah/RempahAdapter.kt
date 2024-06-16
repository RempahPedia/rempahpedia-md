package com.rempahpedia.rempahpedia.ui.rempah

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.data.remote.rempah.RempahResponseItem
import com.rempahpedia.rempahpedia.databinding.RempahItemBinding

class RempahAdapter : ListAdapter<RempahResponseItem, RempahAdapter.ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RempahItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val rempah = getItem(position)
        val binding = holder.binding

        val id = rempah.id
        val nama = rempah.nama
        val img = rempah.imageUrl
        val isRempahUnlocked = rempah.isUnlocked

        if (isRempahUnlocked) {
            Glide.with(holder.itemView.context)
                .load(img)
                .into(holder.binding.rempahImg)
            binding.tvRempahName.text = nama

            holder.itemView.setOnClickListener {
                val detailIntent = Intent(holder.itemView.context, RempahDetailActivity::class.java)
                detailIntent.putExtra(RempahDetailActivity.ID, id)
                holder.itemView.context.startActivity(detailIntent)
            }
        } else {
            binding.rempahImg.setImageResource(R.drawable.ic_lock)
            binding.tvRempahName.text = "????"

            holder.itemView.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "Kamu belum menemukan rempah ini",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    class ListViewHolder(var binding: RempahItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RempahResponseItem>() {
            override fun areItemsTheSame(
                oldItem: RempahResponseItem,
                newItem: RempahResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RempahResponseItem,
                newItem: RempahResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}