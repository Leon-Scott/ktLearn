package com.android.myapplication.bean

/**
 * @author  longbin
 * @date 2024/10/15
 */
class Money(val value:Int) {

    //有趣的运算符重载
    //语法结构如下：
//        operator fun plus(obj: Obj): Obj {
//            // 处理相加的逻辑
//        }
    //重载+ plus
    operator fun plus(money: Money): Money {
        val newValue = value + money.value
        return Money(newValue)
    }

    //重载+ plus，多态
    operator fun plus(money:Int): Money {
        val newValue = value + money
        return Money(newValue)
    }

    //语法糖表达式和实际调用函数对照表
    //语法糖表达式 实际调用函数
    //a + b a.plus(b)
    //a - b a.minus(b)
    //a * b a.times(b)
    //a / b a.div(b)
    //a % b a.rem(b)
    //a++ a.inc()
    //a-- a.dec()
    //+a a.unaryPlus()
    //-a a.unaryMinus()
    //!a a.not()
    //a == b
    //a.equals(b)
    //a > b
    //a < b
    //a >= b
    //a <= b a.compareTo(b)
    //a..b a.rangeTo(b)
    //a[b] a.get(b)
    //a[b] = c a.set(b, c)
    //a in b b.contains(a)
}