package com.example.draganddrop.ui.selected

import android.util.Log
import androidx.lifecycle.*
import com.example.draganddrop.data.model.City
import com.example.draganddrop.data.repository.CityRepository

class SelectedListViewModel(
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _selectedCities = MutableLiveData<List<City>?>(null)
    val selectedCities: LiveData<List<City>?> get() = _selectedCities

    fun filterSelectedItems(ids: List<String>) {
        cityRepository.filterCitiesByIds(ids) {
            Log.d("viewmodel", "filterSelectedItems: ${it.size}")
            _selectedCities.postValue(it)
        }
    }

}