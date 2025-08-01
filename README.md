# 🌦️ Weather App

A modern weather forecasting Android app that provides real-time weather information and 14-day forecasts. Built with XML layouts, Retrofit, and MVVM architecture. The UI is elegant and minimal, designed with a smooth user experience in mind.

![Weather App Screenshot](./preview.png) <!-- Replace with your screenshot path -->

---

## 🚀 Features

- 🌇 Search any city to get real-time weather data.
- 🧠 Smart weather categorization (Sunny, Rain, Snow, etc.).
- 📅 Shows today's date and day.
- 📊 Includes:
  - Temperature
  - Humidity
  - Wind Speed and Direction
  - Visibility
  - Pressure
  - AQI (Air Quality Index)
- ⚡ Smooth animations with Lottie
- 📱 Splash screen with modern look
- 🌐 Powered by [WeatherAPI.com](https://www.weatherapi.com/)

---

## 🛠️ Tech Stack

| Layer           | Tools/Frameworks                |
|----------------|----------------------------------|
| UI             | XML Layouts, ViewBinding         |
| Architecture   | MVVM (Model-View-ViewModel)      |
| Networking     | Retrofit2 + Gson                 |
| Lifecycle      | LiveData, ViewModel              |
| Design         | Lottie Animations, Vector Icons  |
| Language       | Kotlin                           |

---

## 📂 Project Structure
```
com.ionic.weatherappv2/
│
├── data/
│   ├── forecast/                  # Forecast Models
│   │   ├── Astro.kt
│   │   ├── Condition.kt
│   │   ├── Current.kt
│   │   ├── Day.kt
│   │   ├── Forecast.kt
│   │   ├── Forecastday.kt
│   │   ├── Hour.kt
│   │   ├── Location.kt
│   │   └── WeatherForecastModel.kt
│   │
│   ├── realTime/                 # Real-time Weather Models
│   │   ├── AirQuality.kt
│   │   ├── Astro.kt
│   │   ├── Condition.kt
│   │   ├── Current.kt
│   │   ├── Location.kt
│   │   └── WeatherRealTimeModel.kt
│   │
│   └── network/                  # Retrofit Setup
│       ├── NetworkResponse.kt
│       ├── RetrofitInstance.kt
│       └── WeatherApiService.kt
│
├── viewmodel/
│   └── WeatherViewModel.kt       # Business Logic
│
├── MainActivity.kt               # Main UI screen
└── SplashActivity.kt             # Animated splash screen

```
---

## 🔧 Technologies Used

- **Kotlin**
- **XML Layouts**
- **Retrofit** - REST API integration
- **ViewModel** & **LiveData** - lifecycle-aware data handling
- **Material Components** - for UI elements
- **MVVM Architecture**

---

## 🧠 How It Works

1. **User enters a city name**
2. `WeatherViewModel` makes two API calls:
   - `getWeather()` for real-time data
   - `getForecast()` for 14-day forecast
3. API responses are wrapped using a `NetworkResponse` sealed class:
   - `Success`, `Error`, `Loading`
4. UI observes LiveData from ViewModel and updates the screen accordingly.

---
