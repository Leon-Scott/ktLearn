package com.android.myapplication.basicknowledge

import kotlin.reflect.KProperty

/**
 * @author  longbin
 * @date 2024/10/17
 */

fun <T> later(block: () -> T) : Later<T> {
    return Later(block)
}


//这里将getValue()方法的第一个参数指定成了Any?类型，表示我们希望Later的委托功能在
//所有类中都可以使用。然后使用了一个value变量对值进行缓存，如果value为空就调用构造
//函数中传入的函数类型参数去获取值，否则就直接返回。
//由于"懒加载技术"(在哪里体现出来的呀？？？哭)是不会对属性进行赋值的，因此这里我们就不用实现setValue()方法了。
class Later<T>(val block: () -> T) {
    var vproperty: Any? = null
    operator fun getValue(any: Any?, property: KProperty<*>): T {
        vproperty= vproperty ?: block()
        return vproperty as T
    }

}

infix fun String.beginWith(str:String) = startsWith(str)

infix fun <T> Collection<T>.has(value: T) = contains(value)