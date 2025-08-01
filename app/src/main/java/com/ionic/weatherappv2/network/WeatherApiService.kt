package com.ionic.weatherappv2.network

import com.ionic.weatherappv2.data.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apikey : String = "1b68410d0aee42f4ad262259252807",
        @Query("q") city : String = "Ghaziabad",
        @Query("aqi") aqi : String = "yes"
    ) : Response<WeatherModel>
}