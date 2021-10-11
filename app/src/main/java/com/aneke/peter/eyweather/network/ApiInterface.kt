package com.aneke.peter.eyweather.network

import com.aneke.peter.eyweather.network.model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    suspend fun fetchWeatherDataForCity(@Query(value = "q") city : String, @Query(value = "units") units : String = "metric" ,@Query(value = "appid") key : String) : WeatherApiResponse

}