package com.example.draganddrop.data.repository

import android.util.Log
import androidx.lifecycle.*
import com.example.draganddrop.data.model.City
import java.util.concurrent.Executors

class CityRepository {

    private val citiesLiveData = MutableLiveData<List<City>>()
    private val executor = Executors.newSingleThreadExecutor()

    init {
        citiesLiveData.value = mockCities()
    }

    val cities: LiveData<List<City>>
        get() = citiesLiveData


    private fun mockCities(): List<City> {
        return mutableListOf<City>().apply {
            repeat(20) {
                add(
                    City(name = "City${it + 1}", isSelected = false)
                )
            }
        }
    }

    fun removeCity(city: City) {
        val oldList = citiesLiveData.value
        if (oldList != null) {
            executor.submit {
                val newList = oldList - city
                citiesLiveData.postValue(newList)
            }
        }
    }

    fun toggleCitySelection(cityId: String) {
        val oldList = cities.value ?: return
        executor.submit {
            val newList = oldList.map { city ->
                if (city.id == cityId) {
                    city.copy(isSelected = !city.isSelected).also {
                        Log.d("viewmodel", "toggleCitySelection: $cityId, ${it.id}")
                    }
                } else city
            }
            citiesLiveData.postValue(newList)
        }
    }

    fun clearSelection() {
        val oldList = cities.value ?: return
        executor.submit {
            val newList = oldList.map {
                if (it.isSelected) it.copy(isSelected = false)
                else it
            }
            citiesLiveData.postValue(newList)
        }
    }

    fun filterCitiesByIds(
        ids: List<String>,
        onFiltered: (List<City>) -> Unit
    ) {
        val cities = citiesLiveData.value ?: return
        executor.submit {
            val filtered = mutableListOf<City>()
            for (id in ids) {
                val city = cities.find { it.id == id }
                if (city != null) {
                    filtered.add(city.copy(isSelected = false))
                }
            }
            onFiltered(filtered.toList())

        }
    }
}