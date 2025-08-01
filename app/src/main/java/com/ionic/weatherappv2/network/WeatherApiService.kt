package com.ionic.weatherappv2.network

import com.ionic.weatherappv2.data.forecast.WeatherForecastModel
import com.ionic.weatherappv2.data.realTime.WeatherRealTimeModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apikey : String = "1b68410d0aee42f4ad262259252807",
        @Query("q") city : String = "Ghaziabad",
        @Query("aqi") aqi : String = "yes"
    ) : Response<WeatherRealTimeModel>
    suspend fun getForcast(
        @Query("key")apikey : String = "1b68410d0aee42f4ad262259252807",
        @Query("q")city : String = "Ghaziabad",
        @Query("days")days : Int = 14,
        @Query("aqi")aqi : String = "no",
        @Query("alerts")alerts : String = "no"
    ):Response<WeatherForecastModel>
}