package com.aneke.peter.eyweather.main

import com.aneke.peter.eyweather.data.PrefStore
import com.aneke.peter.eyweather.network.ApiInterface
import com.aneke.peter.eyweather.network.model.NetworkResource
import com.aneke.peter.eyweather.utils.resolveMessage
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList

class MainRepository(private val api: ApiInterface) {

    private val cities = listOf("Kenya", "Lesotho", "Cairo", "Jakarta", "Lagos", "Ankara", "Abuja", "Kano",
        "New York", "Peru", "Texas", "Winnipeg", "Amazon", "Bagdad", "Belarus", "Westham")

    suspend fun fetchWeatherData() : List<NetworkResource> {
        try {
            return cities.asFlow()
                .flatMapMerge { city ->
                    flow { emit(fetchWeatherDataForCity(city)) }
                }.toList()
        } catch (e : Exception){
            e.printStackTrace()
            return emptyList()
        }
    }

    private suspend fun fetchWeatherDataForCity(cityName: String) : NetworkResource {
        try {
           return NetworkResource(data= api.fetchWeatherDataForCity(city = cityName, key = PrefStore.apiKey), success = true)
        } catch (e: Exception) {
            return NetworkResource(message = e.resolveMessage())
        }
    }


}