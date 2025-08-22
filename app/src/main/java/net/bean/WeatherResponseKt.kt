package net.bean

data class WeatherResponseKt(
    val Data: Data,
    val Message: String,
    val Tag: Int,
    val Total: Int
)

data class Data(
    val hoursList: List<Hours>,
    val monthList: List<Month>
)

data class Hours(
    val FToDayId: String,
    val FcloudCover: String,
    val FdayOfWeek: String,
    val FdayOrNight: String,
    val Fhumidity: String,
    val server: String
)

data class Month(
    val FToDayId: String,
    val Fdate: String,
    val FdateOfWeek: String,
    val Fday_cloudPct: String,
    val Fday_narrative: String,
    val Fday_phrase: String,
    val FmoonIcon: String,
    val FmoonPhrase: String,
    val Fmoonrise: String,
    val Fmoonset: String,
    val Fsunrise: String,
    val Fsunset: String,
    val server: String
)