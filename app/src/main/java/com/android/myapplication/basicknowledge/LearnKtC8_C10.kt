package com.android.myapplication.basicknowledge

import android.content.Context
import android.content.Intent
import android.content.UriMatcher
import kotlin.concurrent.thread
import kotlin.reflect.KProperty

/**
 * @author  longbin
 * @date 2024/10/17
 * 泛型，类委托和委托属性，infix,协变，逆变
 */
fun main(){

    val fruit = listOf("Apple", "banana", "Orange", "pear", "Grape")

    //8.0Kotlin课堂：泛型和委托
    //泛型主要有两种定义方式：一种是定义泛型类，另一种是定义泛型方法，使用的语法结构都是
    //<T>。当然括号内的T并不是固定要求的，事实上你使用任何英文字母或单词都可以，但是通常
    //情况下，T是一种约定俗成的泛型写法。
    //泛型类
    val data = Data<Int>()
    data.dataPlus(9)
    //泛型方法
   funT("")

    //类委托 by
    val set = MySet<Int>(HashSet())
    //属性委托
    //委托属性的核心思想是将一个属性（字段）的具体实现委托给另一个类去完成。
    //Delegate :委托
    var p by Delegate()

    //示例来学习一下委托功能具体的应用。
    //8.5.3 实现一个自己的lazy函数.Later
    //可以看到，这里使用by关键字连接了左边的p属性和右边的Delegate实例，这是什么意思呢？
    //这种写法就代表着将p(uriMatcher)属性的具体实现委托给了Delegate(later)类去完成。当调用p属性的时候会自
    //动调用Delegate类的getValue()方法，当给p属性赋值的时候会自动调用Delegate类的setValue()方法。
    val uriMatcher by later {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI("","",9)
        matcher
    }

    //9.0Kotlin课堂：使用infix函数构建更可读的语法
    //to并不是Kotlin语言中的一个关键字，之所以我们能够使用A to B这样的语法结构，是
    //因为Kotlin提供了一种高级语法糖特性：infix函数。当然，infix函数也并不是什么难理解的
    //事物，它只是把编程语言函数调用的语法规则调整了一下而已，比如A to B这样的写法，实际
    //上等价于A.to(B)的写法。
    //infix函数允许我们将函数调用时的小数点、括号等计算机相关的语法去掉，从而使用一种更
    //接近英语的语法来编写程序，让代码看起来更加具有可读性。
    "hello,kt".startsWith("hello")
    //infix 版本
    "hello,kt" beginWith "hello"
    //另外，infix函数由于其语法糖格式的特殊性，有''两个比较严格的限制''：首先，infix函数是
    //不能定义成顶层函数的，它必须是某个类的成员函数，可以使用扩展函数的方式将它定义到某
    //个类当中；其次，infix函数必须接收且只能接收一个参数，至于参数类型是没有限制的。只
    //有同时满足这两点，infix函数的语法糖才具备使用的条件
    //优化 list contains 函数为infix函数
    fruit.contains("Banana")
    //infix版本
    fruit has "Banana"


    //thread是一个Kotlin内置的顶层函数，我们只需要在Lambda表达式中编写具体的逻辑
    //就可以了，连start()方法都不用调用，thread函数在内部帮我们全部都处理好了。
    thread {
        //...do work
    }

    //10Kotlin课堂：泛型的高级特性
    //''对泛型进行实化'',而如果我们想要深刻地理解泛型实化，就要先解释一下Java的泛型擦除机制才行。
    //泛型擦除，Java的泛型功能是通过类型擦除机制来实现的。什么意思呢？就是说泛型对于类
    //型的约束只在编译时期存在，运行的时候仍然会按照JDK 1.5之前的机制来运行，JVM是识别不
    //出来我们在代码中指定的泛型类型的。例如，假设我们创建了一个List<String>集合，虽然
    //在编译时期只能向集合中添加字符串类型的元素，但是在运行时期JVM并不能知道它本来只打算
    //包含哪种类型的元素，只能识别出来它是个List。
    //所有基于JVM的语言，它们的泛型功能都是通过类型擦除机制来实现的，其中当然也包括了
    //Kotlin。这种机制使得我们不可能使用a is T或者T::class.java这样的语法，因为T的实际
    //类型在运行的时候已经被擦除了。
    //然而不同的是，Kotlin提供了一个内联函数的概念，我们在第6章的Kotlin课堂中已经学过了这
    //个知识点。内联函数中的代码会在编译的时候自动被替换到调用它的地方，这样的话也就不存
    //在什么泛型擦除的问题了，因为代码在编译之后会直接使用实际的类型来替代内联函数中的泛
    //型声明
    //这就意味着，Kotlin中是可以将内联函数中的泛型进行实化的。
    //那么具体该怎么写才能将泛型实化呢？首先，该函数必须是内联函数才行，也就是要用inline
    //关键字来修饰该函数。其次，在声明泛型的地方必须加上reified关键字来表示该泛型要进行
    //实化。示例代码如下：
    getGenericType<String>()
    getGenericType2("")
    //获取泛型实际类型的功能，代码如下所示：
    val result1 = getGenericType3<String>()
    val result2 = getGenericType3<Int>()
    println("result1 is $result1")
    println("result2 is $result2")
    //泛型实化的应用,举例startActivity,代码见MainActivity




}

class Data<T>{
    fun dataPlus(param:T):T{
        return param
    }
}

fun <T> funT(param: T): T {
    return param
}

//Kotlin还允许我们对泛型的类型进行限制。目前你可以将method()方法的泛型指定成任意类
//型，但是如果这并不是你想要的话，还可以通过指定上界的方式来对泛型的类型进行约束，比
//如这里将method()方法的泛型上界设置为Number类型
fun <T : Number> method(param: T): T {
    return param
}
//这种写法就表明，我们只能将method()方法的泛型指定成数字类型，比如Int、Float、
//Double等。但是如果你指定成字符串类型，就肯定会报错，因为它不是一个数字。
//另外，在默认情况下，所有的泛型都是可以指定成可空类型的，这是因为在不手动指定上界的
//时候，泛型的上界默认是Any?。而如果想要让泛型的类型不可为空，只需要将泛型的上界手动
//指定成Any就可以了。

//类委托和委托属性
//委托是一种设计模式，它的基本理念是：操作对象自己不会去处理某段逻辑，而是会把工作委
//托给另外一个辅助对象去处理。
//Kotlin中也是支持委托功能的，并且将委托功能分为了两种：类委托和委托属性。
//类委托，它的核心思想在于将一个类的具体实现委托给另一个类去完成。在前面的章
//节中，我们曾经使用过Set这种数据结构，它和List有点类似，只是它所存储的数据是无序
//的，并且不能存储重复的数据。Set是一个接口，如果要使用它的话，需要使用它具体的实现
//类，比如HashSet。而借助于委托模式，我们可以轻松实现一个自己的实现类。比如这里定义
//一个MySet，并让它实现Set接口
class MySet<T>(val helperSet: HashSet<T>) : Set<T> {
    override val size: Int
        get() = helperSet.size

    override fun contains(element: T) = helperSet.contains(element)
    override fun containsAll(elements: Collection<T>) = helperSet.containsAll(elements)
    override fun isEmpty() = helperSet.isEmpty()
    override fun iterator() = helperSet.iterator()
}
//如果我们只是让大部分的方法实现调用辅助对象中的方法，少
//部分的方法实现由自己来重写，甚至加入一些自己独有的方法，那么MySet就会成为一个全新
//的数据结构类，这就是委托模式的意义所在。
//但是这种写法也有一定的弊端，如果接口中的待实现方法比较少还好，要是有几十甚至上百个
//方法的话，每个都去这样调用辅助对象中的相应方法实现，那可真是要写哭了。那么这个问题
//有没有什么解决方案呢？在Java中确实没有，但是在Kotlin中可以通过类委托的功能来解决。
//Kotlin中委托使用的''关键字是by''，我们只需要在接口声明的后面使用by关键字，再接上受委托
//的辅助对象，就可以免去之前所写的一大堆模板式的代码了，如下所示：
class MySet2<T>(val helperSet: HashSet<T>) : Set<T> by helperSet {
}
//这两段代码实现的效果是一模一样的，但是借助了类委托的功能之后，代码明显简化了太多。
//另外，如果我们要对某个方法进行重新实现，只需要单独重写那一个方法就可以了，其他的方
//法仍然可以享受类委托所带来的便利，如下所示：
class MySet3<T>(val helperSet: HashSet<T>) : Set<T> by helperSet {
 fun helloWorld() = println("Hello World")
 override fun isEmpty() = false
}


class Delegate {
    val property: Any? = null
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Any {
        return property
    }

    operator fun setValue(nothing: Nothing?, property: KProperty<*>, any: Any) {

    }

}

inline fun <reified T> getGenericType() {
}

inline fun <reified T> getGenericType2(value:T) {
    println("$value")
}

inline fun <reified T> getGenericType3() = T::class.java


inline fun <reified T> startActivityT(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

//泛型的协变  out
//如果某个方法接收一个List<Person>类型的参数，而传入的却是一个
//List<Student>的实例， 在Java中是不允许这么做的。注意这里我的用语，在Java中是不允许这么做的。
//你没有猜错，在Kotlin中这么做是合法的，因为Kotlin已经默认给许多内置的API加上了协变声
//明，其中就包括了各种集合的类与接口。还记得我们在第2章中学过的吗？Kotlin中的List本身
//就是只读的，如果你想要给List添加数据，需要使用MutableList才行。既然List是只读的，也
//就意味着它天然就是可以协变的，
public interface List2<out E> : Collection<E> {
    override val size: Int
    override fun isEmpty(): Boolean
    override fun contains(element: @UnsafeVariance E): Boolean
    override fun iterator(): Iterator<E>
    public operator fun get(index: Int): E
}
//减少类型转换的混乱：例如，在处理集合类型时，如果没有协变，从一个包含子类型元素的集合转换为
// 包含父类型元素的集合可能需要复杂的循环和类型转换操作。而有了协变，这种转换可以更加自然地进行，提高了代码的可读性。

//泛型的逆变  in
interface Transformer<in T> {
    fun transform(t: T): String
}