package com.aneke.peter.eyweather.network.model

data class WeatherApiResponse(
    val base: String = "",
    val cod: Int = 0,
    val coord: Coord = Coord(),
    val dt: Int = 0,
    val id: Int = 0,
    val main: Main = Main(),
    val name: String = "",
    val sys: Sys = Sys(),
    val weather: List<Weather> = listOf(),
)