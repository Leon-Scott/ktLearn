package com.android.myapplication.bean

/**
 * @author  longbin
 * @date 2024/10/14
 */
open class Person(val sex:String) {
    var name = ""
    var age = 0

    open fun eat(){
        println("$name is eating,he is $age years old.")
    }

    //Kotlin规定，当一个类既有主构造函数又有次构造函数时，所有的次构造函数都必须调用主构造 函数（包括间接调用）。
    constructor () :this(""){
        println("this is no param constructor")
    }

}