package net.bean

data class WeatherResponse(
    val Data:WeatherData
)

data class WeatherData(
    val monthList:List<Weather>
)

data class Weather(
    val Fdate:String,
    val FdateOfWeek:String,
    val Fday_phrase:String,
    val Fnight_temperature:String
)
