package net

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityNetBinding
import com.google.gson.Gson
import consts.Constant.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.bean.WeatherRequest
import net.bean.WeatherResponse
import net.client.NetClient
import net.service.WeatherService
import net.viewModel.WeatherViewModel

class NetActivity : AppCompatActivity() {

    lateinit var viewModel:WeatherViewModel
    private val mBinding:ActivityNetBinding by lazy {
        ActivityNetBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        //方式1
//        initListener()
        //方式2 采用viewModel结合的方式
        initListener2()
    }


    fun initListener() {
        mBinding.btGetWeatherData.setOnClickListener() {
            GlobalScope.launch(Dispatchers.IO) {
                getWeatherDataByCity2("深圳", "zh").collect {
                    Log.d("xxx3","result = ${Gson().toJson(it)}")
//                    return@collect
                    withContext(Dispatchers.Main) {
                        mBinding.tvResponse.text = Gson().toJson(it)
                    }
                    val size = it.Data.monthList.size
                    println("month size= $size")
                    for (i in 0 until size) {
                        Log.d("xxx3",
                            "weather date = ${it.Data.monthList[i].Fdate}, week = ${it.Data.monthList[i].FdateOfWeek}" +
                                    ", phrase = ${it.Data.monthList[i].Fday_phrase}}"
                        )
                    }
                }
            }
        }
    }

    fun initListener2(){
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        mBinding.btGetWeatherData.setOnClickListener{
            /**
            //viewModel开启协程作用域,其实没这么回事，理解错了
            //实际上是这样的写法
            1、在viewModel中进行创建时
            一种方法是通过定义    CoroutineScope，Dispatchers.Main--意思是在主线程UI线程上进行调度
            private val viewModelScope = CoroutineScope(Dispatchers.Main)
            private var job: Job? = null

            然后在代码块中进行协程启用
            job = viewModelScope .launch() {
            job.run{-----可以书写你的代码块----}
            }


            一种方法是直接启用
            job = viewModelScope.launch(Dispatchers.Main) {
            job.run{-----可以书写你的代码块----}
            }
            2、当进行取消协程时候
            job.cancel()
             */

            //flow(冷流)的接收和发送需要在一个线程，不然会报错
            CoroutineScope(Dispatchers.IO).launch{
                viewModel.weatherDataFlow.collect {
                    Log.d(TAG,"weather result3= ${Gson().toJson(it)}")
                    withContext(Dispatchers.Main) {
                        mBinding.tvResponse.text = Gson().toJson(it)
                    }
                }
            }

        }
    }


}