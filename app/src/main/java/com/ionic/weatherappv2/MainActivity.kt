package com.ionic.weatherappv2

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

//        viewModel.getData(city = "Ghaziabad")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set system insets if needed
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getData(city = SearchCity().toString())

        // Set date and day
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        binding.date.text = dateFormat.format(Date())

        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        binding.day.text = dayFormat.format(Date())

        // Observe real time weather data
        viewModel.weatherResult.observe(this) { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    Toast.makeText(this, "Please, Wait while I am loading...☺\uFE0F", Toast.LENGTH_SHORT).show()
                }

                is NetworkResponse.Success -> {
                    val weather = result.data
                    val airQuality: AirQuality = result.data.current.air_quality
                    binding.temp.text = "${weather.current.temp_c}°C" //Main Temp
                    binding.cityName.text = weather.location.name // City Name
                    binding.weather.text = weather.current.condition.text // Weather Description
                    binding.humidity.text = "${weather.current.humidity}%" // Humidity
                    binding.windSpeed.text = "${weather.current.wind_kph}km/h" // Wind Speed
                    binding.Pressure.text = "${weather.current.pressure_mb}mb" // Pressure
                    binding.visiblity.text = "${weather.current.vis_km}km" // Visibility
                    binding.aqi.text = "${calculateAQI(airQuality)}" // AQI
                    binding.windDirection.text = weather.current.wind_dir.toString()// Wind Direction

                    when (weather.current.condition.text) {
                        "Clear Sky", "Sunny", "Clear" -> {
                            binding.root.setBackgroundResource(R.drawable.sunny_background)
                            binding.lottieAnimationView.setAnimation(R.raw.sunnny)
                        }

                        "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> {
                            binding.root.setBackgroundResource(R.drawable.cloud_background)
                            binding.lottieAnimationView.setAnimation(R.raw.cloud)
                        }

                        "Light Rain", "Drizzle", "Showers" -> {
                            binding.root.setBackgroundResource(R.drawable.rain_background)
                            binding.lottieAnimationView.setAnimation(R.raw.rain)
                        }

                        "Drizzle", "Heavy Rain" -> {
                            binding.root.setBackgroundResource(R.drawable.rain_background)
                            binding.lottieAnimationView.setAnimation(R.raw.storm)
                        }

                        "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard" -> {
                            binding.root.setBackgroundResource(R.drawable.snow_background)
                            binding.lottieAnimationView.setAnimation(R.raw.snow)
                        }
                    }

                    binding.lottieAnimationView.playAnimation()


                }

                is NetworkResponse.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.forecastResult.observe(this) { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    Toast.makeText(this, "Please, Wait while I am loading...☺\uFE0F", Toast.LENGTH_SHORT).show()
                }
                is NetworkResponse.Success -> {
                    val weather = result.data
                    binding.maxTemp.text = "${weather.forecast.forecastday[0].day.maxtemp_c}°C" // Max Temp
                    binding.maxTemp.text = "${weather.forecast.forecastday[0].day.mintemp_c}°C" // Min Temp
                }
                is NetworkResponse.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun SearchCity() {
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getData(it)

                    // ✅ Hide the keyboard
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(searchView.windowToken, 0)

                    // ✅ Clear focus from search view
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}


