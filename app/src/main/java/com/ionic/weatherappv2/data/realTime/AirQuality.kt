package com.ionic.weatherappv2.data.realTime

data class AirQuality(
    val co: String,
    val no2: String,
    val o3: String,
    val pm10: String,
    val pm2_5: String,
    val so2: String,
)


data class Breakpoint(val cLow: Float, val cHigh: Float, val iLow: Int, val iHigh: Int)

fun getBreakpointAQI(concentration: Float, breakpoints: List<Breakpoint>): Int {
    for (bp in breakpoints) {
        if (concentration in bp.cLow..bp.cHigh) {
            return ((bp.iHigh - bp.iLow) / (bp.cHigh - bp.cLow) * (concentration - bp.cLow) + bp.iLow).toInt()
        }
    }
    return -1 // Invalid/Out-of-range
}

fun calculateAQI(airQuality: AirQuality): Int {
    val pm25Breakpoints = listOf(
        Breakpoint(0f, 30f, 0, 50),
        Breakpoint(31f, 60f, 51, 100),
        Breakpoint(61f, 90f, 101, 200),
        Breakpoint(91f, 120f, 201, 300),
        Breakpoint(121f, 250f, 301, 400),
        Breakpoint(251f, 500f, 401, 500)
    )

    val pm10Breakpoints = listOf(
        Breakpoint(0f, 50f, 0, 50),
        Breakpoint(51f, 100f, 51, 100),
        Breakpoint(101f, 250f, 101, 200),
        Breakpoint(251f, 350f, 201, 300),
        Breakpoint(351f, 430f, 301, 400),
        Breakpoint(431f, 600f, 401, 500)
    )

    val no2Breakpoints = listOf(
        Breakpoint(0f, 40f, 0, 50),
        Breakpoint(41f, 80f, 51, 100),
        Breakpoint(81f, 180f, 101, 200),
        Breakpoint(181f, 280f, 201, 300),
        Breakpoint(281f, 400f, 301, 400),
        Breakpoint(401f, 800f, 401, 500)
    )

    val so2Breakpoints = listOf(
        Breakpoint(0f, 40f, 0, 50),
        Breakpoint(41f, 80f, 51, 100),
        Breakpoint(81f, 380f, 101, 200),
        Breakpoint(381f, 800f, 201, 300),
        Breakpoint(801f, 1600f, 301, 400),
        Breakpoint(1601f, 2000f, 401, 500)
    )

    val coBreakpoints = listOf(
        Breakpoint(0f, 1f, 0, 50),
        Breakpoint(1.1f, 2f, 51, 100),
        Breakpoint(2.1f, 10f, 101, 200),
        Breakpoint(10.1f, 17f, 201, 300),
        Breakpoint(17.1f, 34f, 301, 400),
        Breakpoint(34.1f, 50f, 401, 500)
    )

    val o3Breakpoints = listOf(
        Breakpoint(0f, 50f, 0, 50),
        Breakpoint(51f, 100f, 51, 100),
        Breakpoint(101f, 168f, 101, 200),
        Breakpoint(169f, 208f, 201, 300),
        Breakpoint(209f, 748f, 301, 400),
        Breakpoint(749f, 1000f, 401, 500)
    )

    // Parse strings safely
    val pm25 = airQuality.pm2_5.toFloatOrNull() ?: 0f
    val pm10 = airQuality.pm10.toFloatOrNull() ?: 0f
    val no2 = airQuality.no2.toFloatOrNull() ?: 0f
    val so2 = airQuality.so2.toFloatOrNull() ?: 0f
    val co = airQuality.co.toFloatOrNull() ?: 0f
    val o3 = airQuality.o3.toFloatOrNull() ?: 0f

    // Calculate AQIs
    val aqiList = listOf(
        getBreakpointAQI(pm25, pm25Breakpoints),
        getBreakpointAQI(pm10, pm10Breakpoints),
        getBreakpointAQI(no2, no2Breakpoints),
        getBreakpointAQI(so2, so2Breakpoints),
        getBreakpointAQI(co, coBreakpoints),
        getBreakpointAQI(o3, o3Breakpoints)
    )

    return aqiList.maxOrNull() ?: -1
}
