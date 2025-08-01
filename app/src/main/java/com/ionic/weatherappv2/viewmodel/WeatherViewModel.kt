package com.ionic.weatherappv2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionic.weatherappv2.data.forecast.WeatherForecastModel
import com.ionic.weatherappv2.data.realTime.WeatherRealTimeModel
import com.ionic.weatherappv2.network.NetworkResponse
import com.ionic.weatherappv2.network.RetrofitInstance
import kotlinx.coroutines.launch

class WeatherViewModel :ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherRealTimeModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherRealTimeModel>> = _weatherResult

    private val _forecastResult = MutableLiveData<NetworkResponse<WeatherForecastModel>>()
    val forecastResult : LiveData<NetworkResponse<WeatherForecastModel>> = _forecastResult



    fun getData(city : String){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try{
                val realTimeResponse = weatherApi.getWeather("1b68410d0aee42f4ad262259252807",city)
                val forecastResponse = weatherApi.getForecast("1b68410d0aee42f4ad262259252807",city)
                if(realTimeResponse.isSuccessful && forecastResponse.isSuccessful){
                    realTimeResponse.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                        _forecastResult.value = NetworkResponse.Success(forecastResponse.body()!!)
                    }
                }else{
                    _weatherResult.value = NetworkResponse.Error("Invalid City, or City Not Found! Please Try Again")
                    _forecastResult.value = NetworkResponse.Error("Invalid City, or City Not Found! Please Try Again")
                }
            }
            catch (e : Exception){
                Log.e("WeatherViewModel", "Error loading data", e)
                _weatherResult.value = NetworkResponse.Error("Failed to load data, Maybe your Internet Isn't available")
                _forecastResult.value = NetworkResponse.Error("Failed to load data, Maybe your Internet Isn't available")
            }
        }
    }
}