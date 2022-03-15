package com.example.draganddrop.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.draganddrop.data.model.City
import com.example.draganddrop.databinding.CityItemViewBinding
import com.example.draganddrop.ui.util.CityItemEventListener
import com.example.draganddrop.ui.util.DragAndSwipeCallback

class CityViewHolder(
    private val binding: CityItemViewBinding,
    private val eventListener: CityItemEventListener
) : RecyclerView.ViewHolder(binding.root),
    DragAndSwipeCallback.ItemSwipeListener {

    init {
        binding.content.setOnLongClickListener {
            binding.city?.let { city ->
                eventListener.onItemLongClicked(city)
            } ?: false
        }

        binding.content.setOnClickListener {
            binding.city?.let {
                eventListener.onItemClicked(it)
            }
        }
    }

    fun bind(city: City) {
        binding.city = city
        binding.content.isCheckable = true
        binding.content.isChecked = city.isSelected
        binding.executePendingBindings()
    }

    override fun onItemSwiped() {
        if (binding.city != null) {
            eventListener.onItemRemoved(binding.city!!)
        }
    }
}