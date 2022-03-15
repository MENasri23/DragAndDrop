package com.example.draganddrop.ui.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.draganddrop.data.repository.CityRepository
import com.example.draganddrop.ui.selected.SelectedListViewModel
import java.lang.IllegalArgumentException

class CityListViewModelFactory(
    private val repository: CityRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityListViewModel::class.java)) {
            return CityListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SelectedListViewModel::class.java)) {
            return SelectedListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown type: The class ${modelClass.canonicalName} isn't as CityListViewModel")
    }
}