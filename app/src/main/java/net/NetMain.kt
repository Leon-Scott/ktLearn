package net

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.bean.WeatherInfo
import net.bean.WeatherRequest
import net.bean.WeatherResponse
import net.bean.WeatherResponseKt
import net.client.NetClient
import net.service.WeatherService

fun main() {

    runBlocking {
        getWeatherDataByCity("深圳","zh").collect {
            println("result java = ${Gson().toJson(it)}")
            return@collect
            val size = it.data?.monthList?.size
            println("month size= $size")
            for (i in 0 until size!!){
                println("weather date = ${it.data?.monthList?.get(i)?.fdate}, week = ${it.data?.monthList?.get(i)?.fdateOfWeek}" +
                        ", phrase = ${it.data?.monthList?.get(i)?.fdayPhrase}, temperature = ${it.data?.monthList?.get(i)?.fmoonPhrase}")
            }
        }

        getWeatherDataByCity2("深圳","zh").collect {
            println("result kotlin= ${Gson().toJson(it)}")
            return@collect
            val size = it.Data.monthList.size
            println("month size= $size")
            for (i in 0 until  size){
                println("weather date = ${it.Data.monthList[i].Fdate}, week = ${it.Data.monthList[i].FdateOfWeek}" +
                        ", phrase = ${it.Data.monthList[i].Fday_phrase}}")
            }
        }
    }
}

fun getWeatherDataByCity(city: String, lang: String): Flow<WeatherInfo> = flow {
    val weatherService = NetClient.retrofit.create(WeatherService::class.java)
//    val response = withContext(Dispatchers.IO) {
//        weatherService.getWeatherData(WeatherRequest(city, lang))
//    }
    val response = weatherService.getWeatherData(WeatherRequest(city, lang))

    emit(response)
}.flowOn(Dispatchers.IO)
    .catch {
        Log.e("xxx3",it.message.toString())
    }

fun getWeatherDataByCity2(city: String, lang: String): Flow<WeatherResponse> = flow {
    val weatherService = NetClient.retrofit.create(WeatherService::class.java)
//    val response = withContext(Dispatchers.IO) {
//        weatherService.getWeatherData(WeatherRequest(city, lang))
//    }
    val response = weatherService.getWeatherData2(WeatherRequest(city, lang))

    emit(response)
}.flowOn(Dispatchers.IO)
    .catch {
        Log.e("xxx3",it.message.toString())
    }

