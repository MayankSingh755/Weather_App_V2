package com.ionic.weatherappv2.data.forecast

data class WeatherForecastModel(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)