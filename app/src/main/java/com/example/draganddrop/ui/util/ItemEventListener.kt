package com.example.draganddrop.ui.util

import com.example.draganddrop.data.model.City

interface CityItemEventListener {
    fun onItemClicked(city: City)
    fun onItemLongClicked(city: City): Boolean
    fun onItemRemoved(city: City)
}