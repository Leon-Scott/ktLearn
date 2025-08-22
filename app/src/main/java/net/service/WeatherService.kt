package net.service

import net.bean.WeatherInfo
import net.bean.WeatherRequest
import net.bean.WeatherResponse
import net.bean.WeatherResponseKt
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 使用coroutine,flow,retrofit实现网络请求下载天气数据
 * */
interface WeatherService {

    @Headers("Content-Type:application/json","token:E51BF4B2A022467DB695A83940B1D057")
    @POST("Weather/GetWeatherDataToDay")
    suspend fun getWeatherData(
        @Body request:WeatherRequest,
    ): WeatherInfo

    @Headers("Content-Type:application/json","token:E51BF4B2A022467DB695A83940B1D057")
    @POST("Weather/GetWeatherDataToDay")
    suspend fun getWeatherData2(
        @Body request:WeatherRequest,
    ): WeatherResponse


    //kotlin 接口文件中也可以有实例方法
    fun getSunTemp():Int{
        return 10
    }
}