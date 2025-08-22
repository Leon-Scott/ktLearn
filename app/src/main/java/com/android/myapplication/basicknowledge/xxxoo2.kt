package com.android.myapplication.basicknowledge

import com.android.myapplication.bean.Money
import java.lang.StringBuilder

/**
 * @author  longbin
 * @date 2024/10/15
 */

fun main(){

    val fruit = listOf("Apple", "Banana", "Orange", "Pear", "Grape")

    //现在我们即将进入本书首次的 ''Kotlin课堂''，之后的几乎每一章中都会有这样一个环节。虽说目前
    //你已经可以上手Kotlin编程了，但我们只是在第2章中学习了一些Kotlin的基础知识而已，其实
    //还有许多的高级技巧并没有涉猎。因此每章的Kotlin课堂里，我都会结合所在章节的内容，拓展
    //出更多Kotlin的使用技巧，这将会是你提升自己Kotlin水平的绝佳机会。

    //标准函数with、run和apply .(let 已学  ，repeat ),also,takeIf,takeUnless
    //Kotlin的标准函数指的是Standard.kt文件中定义的函数，任何Kotlin代码都可以自由地调用所有的标准函数。
    //with  场景：吃完所有水果
    //with函数接收两个参数：第一个参数可以是一个任意类型的对
    //象，第二个参数是一个Lambda表达式。with函数会在Lambda表达式中提供第一个参数对象
    //的上下文，并使用Lambda表达式中的最后一行代码作为返回值返回。
    val stringBuilder  = StringBuilder()
    with(stringBuilder){
        append("start eat fruit :")
        fruit.forEach {
            append("$it ; ")
        }
        append("eat finish")
    }
    println(stringBuilder)

    //run。run函数的用法和使用场景其实和
    //with函数是非常类似的，只是稍微做了一些语法改动而已.首先run函数通常不会直接调用，
    //而是要在某个对象的基础上调用；其次run函数只接收一个Lambda参数，并且会在Lambda表
    //达式中提供调用对象的上下文。其他方面和with函数是一样的，包括也会使用Lambda表达式
    //中的最后一行代码作为返回值返回。
    stringBuilder.clear()
    val result = stringBuilder.run {
        append("start eat by runnnnn :")
        fruit.forEach {
            append("$it ->")
        }
        "我不吃了"
    }
    println(result)

    //apply。apply函数和run函数也是极其类似的，都要在某
    //个对象上调用，并且只接收一个Lambda参数，也会在Lambda表达式中提供调用对象的上下
    //文，但是apply函数无法指定返回值，而是会自动返回调用对象本身。
    stringBuilder.clear()
    stringBuilder.apply {
        append("start eat by runnnnn :")
        fruit.forEach {
            append("$it ->")
        }
    }
    println(stringBuilder)

    //repeat  。repeat函数是Kotlin中另外一个非常常用的标准函数，它允许你
    //传入一个数值n，然后会把Lambda表达式中的内容执行n遍。
    repeat(9){
        print("repeat $it ;")
    }
    println()





    //顶层方法，顶层方法指的是那些没有定义在任何类中的方法，比如我们在上一节中（本文件）编写的main()方法。
    // Kotlin编译器会将所有的顶层方法全部编译成静态方法，因此只要你定义了一个顶层方法，那么它就一定是静态方法。
    //调用方法：如果是在Kotlin代码中调用的话，那就很简单了，所有的顶层方法都可以在任何位置被直接调
    //用，不用管包名路径，也不用创建实例，直接键入doSomething()即可。因此在Java中使用HelperKt.doSomething()的写法来调用就可以了（文件名.函数名）
    //顶层方法同包下重名了怎么区分调用。
//    main()  目前无解，同包下不建议有同名的的顶层函数



    //调用扩展函数
    val str = "adb123rg"
    println("str has ${str.countLetter()} letter")


    //调用运算符重载 operat
    val money = Money(5)
    val money2 = Money(10)
    println("money+money2 = ${(money+money2).value}")
    println("money+int = ${(money+4).value}")

    println("str repeat (2..5).random() times=${str.repeat((2..5).random())}")//此处repeat不是标准函数库中的repeat.
    // 标准函数库中的直接repeat没有返回值
}

//Kotlin课堂：扩展函数和运算符重载
//扩展函数的语法结构.定义扩展函数只需要在函数名的前面加上一个''ClassName.''的语
//法结构，就表示将该函数添加到指定类当中了
//fun ClassName.methodName(param1: Int, param2: Int): Int {
//    return 0
//}
//使用扩展函数的方式来优化刚才的统计某个字符串中的字母数量功能
fun String.countLetter(): Int {
    var count = 0
    this.forEach {
        if (it.isLetter()) count++
    }
    return count
}
//由于我们希望向String类中添加一个扩展函数，因此需要先创建一个String.kt文件。文件名虽
//然并没有固定的要求，但是我建议向哪个类中添加扩展函数，就定义一个同名的Kotlin文件，这
//样便于你以后查找。当然，扩展函数也是可以定义在任何一个现有类当中的，并不一定非要创
//建新文件。不过通常来说，最好将它定义成顶层方法，这样可以让扩展函数拥有全局的访问域。


//定义静态方法
//和绝大多数主流编程语言不同的是，Kotlin却极度弱化了静态方法这个概念，想要在Kotlin中定义一个静态方法反倒不是一件容易的事。
//那么Kotlin为什么要这样设计呢？因为Kotlin提供了比静态方法更好用的语法特性，并且我们在上一节中已经学习过了，那就是单例类。
//因此如果你在'Java代码'中以静态方法的形式去调用的话，你会发现这些方法并不存在。
// 而如果我们给单例类或companion object中的方法加上@JvmStatic注解，那么Kotlin编译器就会将这些方法编译成真正的静态方法
/*
companion object Worker{
    @JvmStatic
    fun doAction2() {
        println("do action2")
    }
}*/
