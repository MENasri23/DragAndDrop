package com.example.draganddrop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.draganddrop.data.model.City
import com.example.draganddrop.databinding.CityItemViewBinding
import com.example.draganddrop.ui.util.CityItemEventListener

class CityAdapter(
    private val cityEventListener: CityItemEventListener
) : ListAdapter<City, CityViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return CityViewHolder(
            CityItemViewBinding.inflate(inflater, parent, false),
            cityEventListener
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(city = getItem(position))
    }

    object COMPARATOR : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }
}