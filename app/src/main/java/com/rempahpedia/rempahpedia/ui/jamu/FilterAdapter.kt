package com.rempahpedia.rempahpedia.ui.jamu

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rempahpedia.rempahpedia.R

class FilterAdapter(
    private val filters: List<String>,
    private val onFilterChangeListener: (Int?) -> Unit
) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private var selectedPosition: Int = 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val filterToggleText: TextView = view.findViewById(R.id.filterToggleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter_toggle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.filterToggleText.text = filters[position]
        val isSelected = selectedPosition == position
        updateBackground(holder.itemView, isSelected)
        updateTextColor(holder.filterToggleText, isSelected)
        updateTextStyle(holder.filterToggleText, isSelected)

        holder.itemView.setOnClickListener {
            if (selectedPosition != position) {
                val previousSelectedPosition = selectedPosition
                selectedPosition = position

                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(position)

                onFilterChangeListener(selectedPosition)
            }
        }
    }

    override fun getItemCount() = filters.size

    private fun updateBackground(view: View, isSelected: Boolean) {
        view.setBackgroundResource(
            if (isSelected) R.drawable.filter_toggle_selected else R.drawable.filter_toggle_unselected
        )
    }

    private fun updateTextColor(textView: TextView, isSelected: Boolean) {
        @Suppress("DEPRECATION")
        textView.setTextColor(
            textView.context.resources.getColor(
                if (isSelected) R.color.white else R.color.black
            )
        )
    }

    private fun updateTextStyle(textView: TextView, isSelected: Boolean) {
        textView.setTypeface(null, if (isSelected) Typeface.BOLD else Typeface.NORMAL)
    }
}

