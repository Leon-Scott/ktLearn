package com.android.myapplication.myinterface

/**
 * @author  longbin
 * @date 2024/10/14
 */
interface Study {
    fun readBook()

    fun doHomeWork()

    //Kotlin还增加了一个额外的功能：允许对接口中定义的函数进行默认实现,类似接口和抽象类的结合
    fun doHomeWorkMath(){
        println("doHomeWorkMath from interface")
    }
}