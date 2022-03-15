package com.example.draganddrop.di

import com.example.draganddrop.data.repository.CityRepository

object AppContainer {

    private var cityRepository: CityRepository? = null

    fun provideCityRepository() = cityRepository ?: synchronized(this) {
        cityRepository ?: CityRepository().also { cityRepository = it }
    }
}