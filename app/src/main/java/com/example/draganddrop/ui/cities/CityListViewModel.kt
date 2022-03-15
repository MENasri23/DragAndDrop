package com.example.draganddrop.ui.cities

import androidx.lifecycle.*
import com.example.draganddrop.data.model.City
import com.example.draganddrop.data.repository.CityRepository

class CityListViewModel(
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _selectedIds = MutableLiveData<List<String>>()
    val selectedIds: LiveData<List<String>> get() = _selectedIds

    val cities = cityRepository.cities

    val selectedCount = selectedIds.map { it.size }

    fun removeCity(city: City) {
        cityRepository.removeCity(city)
        val selectedIds = selectedIds.value ?: return
        if (city.id in selectedIds) {
            _selectedIds.value = selectedIds - city.id
        }
    }

    fun onLongSelect(city: City): Boolean {
        if (!hasSelectedItem()) {
            _selectedIds.value = listOf(city.id)
            cityRepository.toggleCitySelection(city.id)
            return true
        }
        return false
    }

    fun toggleCitySelection(cityId: String) {
        if (hasSelectedItem()) {
            cityRepository.toggleCitySelection(cityId)
            val selectedIds = selectedIds.value ?: return
            _selectedIds.value = if (cityId in selectedIds) {
                selectedIds - cityId
            } else {
                selectedIds + cityId
            }
        }
    }

    fun hasSelectedItem(): Boolean {
        val selectedCount = selectedCount.value ?: 0
        return (selectedCount > 0)
    }

    fun clearSelection() {
        cityRepository.clearSelection()
        _selectedIds.value = emptyList()
    }


}