package net.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import consts.Constant.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import net.bean.WeatherRequest
import net.bean.WeatherResponse
import net.client.NetClient
import net.service.WeatherService

class WeatherViewModel : ViewModel() {

    val weatherDataFlow = flow {
        withContext(Dispatchers.IO) {
            emit(
                NetClient.retrofit.create(WeatherService::class.java)
                    .getWeatherData2(WeatherRequest("北京", "zh"))
            )
        }
    }.onStart {
        Log.d(TAG, "weather flow start.")
    }
//        .filter {
//        it.Data.monthList.size > 2
//    }.map {
//        it.Data.monthList.listIterator().forEach {
//            it.Fdate + "~~"
//        }
//    }.onCompletion {
//        Log.d(TAG, "weather flow completion.")
//    }

    fun testViewModelScope(){

    }

}