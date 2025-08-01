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
com.ionic.weatherappv2/
├── data/
│ ├── forecast/ # Forecast Models
│ ├── realTime/ # Real-time Weather Models
│ └── network/ # Retrofit setup
│ ├── NetworkResponse
│ ├── RetrofitInstance
│ └── WeatherApiService.kt
│
├── viewmodel/
│ └── WeatherViewModel.kt # Business Logic
│
├── MainActivity.kt # Main UI screen
└── SplashActivity.kt # Animated splash screen
