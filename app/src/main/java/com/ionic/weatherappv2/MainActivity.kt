package com.ionic.weatherappv2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.ionic.weatherappv2.data.realTime.AirQuality
import com.ionic.weatherappv2.data.realTime.calculateAQI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.ionic.weatherappv2.databinding.ActivityMainBinding
import com.ionic.weatherappv2.network.NetworkResponse
import com.ionic.weatherappv2.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set system insets (status/navigation bar paddings)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }

        setupSearchView()
        setupDateAndDay()

        // Initial weather load
        viewModel.getData("Ghaziabad")

        // Observe Real-Time Weather Data
        viewModel.weatherResult.observe(this) { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    Toast.makeText(this, "Please wait... ☺️", Toast.LENGTH_SHORT).show()
                }

                is NetworkResponse.Success -> {
                    val weather = result.data
                    val airQuality = weather.current.air_quality

                    binding.temp.text = "${weather.current.temp_c}°C"
                    binding.cityName.text = weather.location.name
                    binding.weather.text = weather.current.condition.text
                    binding.humidity.text = "${weather.current.humidity}%"
                    binding.windSpeed.text = "${weather.current.wind_kph} km/h"
                    binding.Pressure.text = "${weather.current.pressure_mb} mb"
                    binding.visiblity.text = "${weather.current.vis_km} km"
                    binding.aqi.text = calculateAQI(airQuality).toString()
                    binding.windDirection.text = weather.current.wind_dir

                    applyWeatherTheme(weather.current.condition.text)
                    binding.lottieAnimationView.playAnimation()
                }

                is NetworkResponse.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observe Forecast Data
        viewModel.forecastResult.observe(this) { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    Toast.makeText(this, "Fetching forecast... ☺️", Toast.LENGTH_SHORT).show()
                }

                is NetworkResponse.Success -> {
                    val forecast = result.data.forecast.forecastday[0].day
                    binding.maxTemp.text = "Max Temp Today${forecast.maxtemp_c}°C"
                    binding.minTemp.text = "Min Temp Today${forecast.mintemp_c}°C"
                }

                is NetworkResponse.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setupSearchView() {
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getData(it)
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(searchView.windowToken, 0)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
    }

    private fun setupDateAndDay() {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        binding.date.text = dateFormat.format(Date())
        binding.day.text = dayFormat.format(Date())
    }

    private fun applyWeatherTheme(conditionText: String) {
        val condition = conditionText.lowercase()

        when {
            condition.contains("clear") || condition.contains("sunny") -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sunnny)
            }

            condition.contains("cloud") || condition.contains("fog") || condition.contains("mist") || condition.contains("overcast") -> {
                binding.root.setBackgroundResource(R.drawable.cloud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }

            condition.contains("rain") || condition.contains("drizzle") || condition.contains("shower") -> {
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }

            condition.contains("snow") || condition.contains("blizzard") -> {
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }

            else -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sunnny)
            }
        }
    }
}
