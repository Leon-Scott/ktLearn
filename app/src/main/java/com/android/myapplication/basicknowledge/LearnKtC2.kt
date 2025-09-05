package com.android.myapplication.basicknowledge

import kotlin.math.max
import kotlin.math.min

fun main(){
    val a = 10 //△1 变量val,var
   // a = 11 //Val cannot be reassigned
    var b:Int = 11
//    print("hello,a=$a")
    println("max number=${largeNumber(a,b)}")//△重要：字符串拼接
    println("min number=${minNumber2(a,b)}")
    println("get TomScore=${getScore("Tom")}")
    println("get TomScore=${getScore("Tome")}")

    checkNumber(0.4565)
    checkNumber(4565L)

    println("get TomScore2=${getScoreWhen2("Tome")}")

    //for循环,如果有一些特殊场景使用for in循环无法实现的话，我们还可以改用while循环的方式来进行实现,
    // 其中while循环不管是在语法还是使用技巧上都和Java中的while循环没有任何区别
    val range = 0..10 //闭包区间
    val range2 = 0 until 10 //左闭右开区间
    println("..闭包区间")
    for (i in range){
        print("$i ")
    }
    println("\nuntil 左闭右开区间")
    for (i in range2){
        print("$i ")
    }
    println("\nstep关键字可以跳过循环中的某些元素 step 3")
    //step关键字可以跳过循环中的某些元素,step 2 类似于 i+2
    for (i in 0..10 step 3) {
        print("$i ")
    }
    println("\ndownTo 降序 step2")
    //downTo 关键字遍历降序闭包区间
    for (i in 10 downTo -2 step 2) print("$i ")
    println()

}

fun largeNumber(num:Int,num2:Int):Int{ //3 方法声明，传参，返回
    return max(num,num2)
}

fun minNumber2(num: Int,num2: Int) :Int= min(num,num2)//△方法体只有一行的缩写形式

fun largeNum3(num:Int,num2:Int):Int{ //
    var value = if(num > num2){ //Kotlin中的if语句相比于Java有一个额外的功能，它是可以有返回值的，
        // 返回值就是if语句每一个条件中最后一行代码的返回值
        num
    } else {
        num2
    }
    return value
}

//largeNumber3精简版 ,return关键字也可以省略了，###等号足以表达返回值的意思###
//Kotlin出色的类型推导机制吗？在这里它也可以发挥重要的作用。由于max()函数返回的是一个
//Int值，而我们在largerNumber()函数的尾部又使用等号连接了max()函数，因此Kotlin可
//以推导出largerNumber()函数返回的必然也是一个Int值，这样就不用再显式地声明返回值
//类型了，代码可以进一步简化成如下形式
fun largeNumber33(num:Int,num2:Int) = if(num > num2) num else num2

//when 高级版switch,匹配值 -> { 执行逻辑 }
fun getScore(name:String) = when(name){
    "Tom"->88
    "Jack"->77
    "Jim"->66
    else -> 100
}

//除了精确匹配之外，when语句还允许进行类型匹配,is相当于instanceOf,Number抽象类（像Int、Long、Float、Double等与数字相关的类都是它的子类）
fun checkNumber(num:Number) {
    when(num){
        is Int -> println("num is Int")
        is Double -> println("num is Double")
        is Long -> println("num is Long")
        else -> println("num is unknown type")
    }
}

//其实when语句还有一种不带参数的用法,这种用法是将判断的表达式完整地写在when的结构体当中.Kotlin中判断字符串或对象是否相等可以直接使用==关键字，而不用像Java那样调用equals()方法
fun getScoreWhen(name:String) :Int{
    return when{
        name.startsWith("Tom") ->88
        name == "Jack" -> 77
        name == "Jim" -> 66
        else -> 100
    }
}
//getScoreWhen语法糖简写版本，去掉返回类型直接=
fun getScoreWhen2(name:String) = when {
        name.startsWith("Tom") ->88
        name == "Jack" -> 77
        name == "Jim" -> 66
        else -> 100
    }