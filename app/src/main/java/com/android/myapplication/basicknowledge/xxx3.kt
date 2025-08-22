package com.android.myapplication.basicknowledge

import android.content.*
import androidx.core.content.contentValuesOf
import com.android.myapplication.myinterface.Result
import com.android.myapplication.myinterface.Success
import java.io.*
import java.lang.StringBuilder

/**
 * @author  longbin
 * @date 2024/10/16
 */

fun main() {
    val fruit = listOf("Apple", "banana", "Orange", "pear", "Grape")


    //Kotlin课堂：高阶函数详解
    //高阶函数的定义。如果一个函数接收另一个函数作为参数，或者返回值的类型是
    //另一个函数，那么该函数就称为高阶函数。
    //函数类型的语法规则是有点特殊的，基本规则如下：
    //(String, Int) -> Unit
    //既然是定义一个函数类型，那么最关键的就是要声明该函数接收什么参数，以及它的返回值是
    //什么。因此，->左边的部分就是用来声明该函数接收什么参数的，多个参数之间使用逗号隔
    //开，如果不接收任何参数，写一对空括号就可以了。而->右边的部分用于声明该函数的返回值
    //是什么类型，如果没有返回值就使用Unit，它大致相当于Java中的void。
    fun example(func: (String, Int) -> Unit) {
        func("hello", 123)
    }
    //由于高阶函数的
    //用途''实在是太广泛了''，这里如果要让我简单概括一下的话，那就是高阶函数允许让函数类型的
    //参数来决定函数的执行逻辑。即使是同一个高阶函数，只要传入不同的函数类型参数，那么它
    //的执行逻辑和最终的返回结果就可能是完全不同的。

    //Lambda表达式同样可以完整地表达一个函数的参数声明和返回值声明
    //（Lambda表达式中的最后一行代码会自动作为返回值）
    //val result = num1AndNum2(num1, num2, ::plus) //::plus 是函数名（函数体）
    val numPlus = num1AndNum2(3, 4) { n, m -> n + m }
    println("numPlus = $numPlus")

    //高阶函数五个事例，
    // 1、 StringBuilder.build（StringBuilder.apply2）方法代替apply,吃水果
    var strBuilder: StringBuilder = StringBuilder().apply2() {
        append("start eat fruit:")
        for (fruit in fruit) append(" $fruit ;")
        append("eat finish.")
    }
    println("highOrderFunction 1 StringBuilder: $strBuilder")



    //2、高阶函数使用场景二：使用协程网络请求后可以使用高阶函数来简化回调的处理。类似于callBack Listener的作用
    //    Suspend function 'performNetworkRequest' should be called only from a coroutine or another suspend function
//            performNetworkRequest { result ->
//                when (result) {
//                    is Success -> println(result.msg)
//                    is Failure -> println(result.error.message)
//                }
//            }

    //3、定义一个高阶函数对列表中的元素进行特定的转换：it是此处的泛型参数
    val transformList = transformData(fruit){
        it + "☆"
    }
    transformData(listOf(1, 2, 3, 4, 5)) { n -> n * 3 }
    println(transformList)

    //Kotlin高阶函数背后的实现原理。你会发现，原来我们一直使用的Lambda表达式在底层
    //被转换成了匿名类的实现方式。这就表明，我们每调用一次Lambda表达式，都会创建一个新
    //的匿名类实例，当然也会造成额外的内存和性能开销。
    //为了解决这个问题，Kotlin提供了内联函数的功能，它可以将使用Lambda表达式带来的运行时
    //开销完全消除。
    //内联函数的用法非常简单，只需要在定义高阶函数时加上inline关键字的声明即可


    //前面我们已经解释了内联函数的好处，那么为什么Kotlin还要提供一个noinline关键字来排除
    //内联功能呢？这是因为内联的函数类型参数在编译的时候会被进行代码替换，因此它没有真正
    //的参数属性。非内联的函数类型参数可以自由地传递给其他任何函数，因为它就是一个真实的
    //参数，而内联的函数类型参数只允许传递给另外一个内联函数，这也是它最大的局限性。
    inlineTest({}){}

//内联函数和非内联函数还有一个重要的区别，那就是内联函数所引用的Lambda表达式
//中是可以使用return关键字来进行函数返回的，而非内联函数只能进行局部返回。
    val str = ""
    printString(str) { s ->
        println("lambda start")
        if (s.isEmpty()) return@printString
        println(s)
        println("lambda end")
    }


    //这里还使用了一个use函数，这是Kotlin提供的一个内置扩展函数。它会保证在Lambda
    //表达式中的代码全部执行完之后自动将外层的流关闭，这样就不需要我们再编写一个finally
    //语句，手动去关闭流了，是一个非常好用的扩展函数。
    try {
//        val output = openFileOutput("data", Context.MODE_PRIVATE)
        val output = File("").outputStream()
        val writer = BufferedWriter(OutputStreamWriter(output))
        val inputText = "hello,am loon"
        writer.use {
            it.write(inputText)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    //另外，Kotlin是没有异常检查机制（checked exception）的。这意味着使用Kotlin编写的所
    //有代码都不会强制要求你进行异常捕获或异常抛出。上述代码中的try catch代码块是参照
    //Java的编程规范添加的，即使你不写try catch代码块，在Kotlin中依然可以编译通过。


    //这里从文件中读取数据使用了一个forEachLine函数，这也是Kotlin提供的一个内置扩
    //展函数，它会将读到的每行内容都回调到Lambda表达式中，我们在Lambda表达式中完成拼
    //接逻辑即可。
//    以下是一段简单的代码示例，展示了如何从文件中读取文本数据：
    fun load(): String {
        val content = StringBuilder()
        try {
//            val input = openFileInput("data")
            val input = File("").inputStream()
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append(it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }


    //arrayOf()方法是Kotlin提供的一种用于便捷创建数组的内置方法
    arrayOf("The Da Vinci Code","kt lang")

    //本节Kotlin课堂里会使用高阶函数简化SharedPreferences和
    //ContentValues这两种API的用法，让它们的使用变得更加简单
    //，在开始对它进行简化之前，我们先回顾一下SharedPreferences原来的用法。
   spHiOrderFun()
    //当然，最后不得不提的是，其实Google提供的KTX扩展库中已经包含了上述
    //SharedPreferences的简化用法，这个扩展库会在Android Studio创建项目的时候自动引入
    //build.gradle的dependencies中
    //因此，我们实际上可以直接在项目中使用如下写法来向SharedPreferences存储数据：
/*    val context = object : Context() {
    }
    val sharedPreference = context.getSharedPreferences("loonData",Context.MODE_PRIVATE).edit {
        putBoolean("isTuesday",true)
    }*/
    //???标准版本有疑问，为什么空参数高阶函数，调用的时候穿了一个参数editor????



    //简化ContentValues的用法
//    val values = cvOf("name" to "Game of Thrones", "author" to "George Martin",
//        "pages" to 720, "price" to 20.85)
//    db.insert("Book", null, values)
    //KTX库中也提供了一个具有同样功能的contentValuesOf()方法，用法如下所示：
    val valuesKtx = contentValuesOf("name" to "Game of Thrones", "author" to "George Martin",
     "pages" to 720, "price" to 20.85)
    //db.insert("Book", null, values)

    //by lazy代码块是Kotlin提供的一种懒加载技术，代码块中的代码一开始
    //并不会执行，只有当uriMatcher变量首次被调用的时候才会执行，并且会将代码块中最后一
    //行代码的返回值赋给uriMatcher。
    val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
//        matcher.addURI(authority, "book", bookDir)
//        matcher.addURI(authority, "book/#", bookItem)
//        matcher.addURI(authority, "category", categoryDir)
//        matcher.addURI(authority, "category/#", categoryItem)
        matcher
    }



}

//fun num1AndNum2(num1: Int, num2: Int, highFunc: (n: Int, m: Int) -> Int) :Int{
inline fun num1AndNum2(num1: Int, num2: Int, highFunc: (Int, Int) -> Int): Int {
    return highFunc(num1, num2)
}

//它在函数类型的前面加
//上了一个StringBuilder. 的语法结构。
//其实这才是定义高阶函数完整的语
//法规则，在函数类型的前面加上ClassName. （StringBuilder.）就表示这个函数类型是定义在哪个类当中的。
fun StringBuilder.apply2(
    string: StringBuilder = StringBuilder(),
    buildx: StringBuilder.() -> StringBuilder
): StringBuilder {
    return buildx() //这里是扩展函数写法。另一种写法build函数没有返回值改为Unit，此行返回this。效果一样
}

//
//2、异步网络请求  suspend挂起，resume回复
suspend fun performNetworkRequest(callback: (Result) -> Unit) {
    // 模拟网络请求
//    delay(1000)
    callback(Success("Network response"))
}


//3、数组转换
fun <T> transformData(list: List<T>, transform: (T) -> T): List<T> {
    return list.map { transform(it) }
}

//这里使用inline关键字声明了inlineTest()函数，原本block1和block2这两
//个函数类型参数所引用的Lambda表达式都会被内联。但是我们在block2参数的前面又加上了
//一个noinline关键字，那么现在就只会对block1参数所引用的Lambda表达式进行内联了。
//这就是noinline关键字的作用。
inline fun inlineTest(block1: () -> Unit, noinline block2: () -> Unit) {
}


fun printString(str: String, block: (String) -> Unit) {
    println("printString begin")
    block(str)
    println("printString end")
}

//使用内联函数可能出现的错误
//首先，在runRunnable()函数中，我们创
//建了一个Runnable对象，并在Runnable的Lambda表达式中调用了传入的函数类型参数。而
//Lambda表达式在编译的时候会被转换成匿名类的实现方式，也就是说，上述代码实际上是在
//匿名类中调用了传入的函数类型参数。
//而内联函数所引用的Lambda表达式允许使用return关键字进行函数返回，但是由于我们是在
//匿名类中调用的函数类型参数，此时是不可能进行外层调用函数返回的，最多只能对匿名类中
//的函数调用进行返回，因此这里就提示了上述错误。
//比如借助crossinline关键
//字就可以很好地解决这个问题：那么这个crossinline关键字又是什么呢？前面我们已经分析过，之所以会提示图6.18所示的
//错误，就是因为内联函数的Lambda表达式中允许使用return关键字，和高阶函数的匿名类实
//现中不允许使用return关键字之间造成了冲突。而crossinline关键字就像一个契约，它用
//于保证在内联函数的Lambda表达式中一定不会使用return关键字，这样冲突就不存在了，问
//题也就巧妙地解决了。
inline fun runRunnable(crossinline block: () -> Unit) :String{
    val runnable = Runnable {
        block()
    }
    runnable.run()
    return ""
}

fun spHiOrderFun(){
//    val context = object : Context() {
//    }
//    val sharedPreference = context.getSharedPreferences("loonData",Context.MODE_PRIVATE)
//    val editor = sharedPreference.edit()
//    editor.putBoolean("isRainDay",false)
//    editor.apply()
//
//    //优化后
//    sharedPreference.putData {
//        this.putBoolean("isSunnyDay",true)
//    }
}

//putDateBlock 在表达式中不能单独调用了，会报错。加了关键词前缀SharedPreferences.Editor.后它属于editor的方法了。
inline fun SharedPreferences.putData(putDateBlock: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.putDateBlock()
    //或者
    //putDateBlock(editor)这两种写法是等效的.郭神亲自回复的答案哦~
    editor.apply()
}

//Pair类型。由于Pair是一种键值对的数据结构，因此需要通过泛型来指定它的键和值分别对应什么类型的数据
//vararg对应的就是Java中的可变参数列表，我们允许向这个方法传入0个、1个、2个甚至任意多个Pair类型的参数
//Any?。这是因为Any是Kotlin中所有类的共同基类，相当于Java中的Object，而Any?则表示允许传入空值。
fun cvOf(vararg pairs: Pair<String, Any?>) = ContentValues().apply {
    //apply 返回对象本身
    for (pair in pairs) {
        val key = pair.first
        val value = pair.second
        when (value) {
            is Int -> put(key, value)
            is Long -> put(key, value)
            is Short -> put(key, value)
            is Float -> put(key, value)
            is Double -> put(key, value)
            is Boolean -> put(key, value)
            is String -> put(key, value)
            is Byte -> put(key, value)
            is ByteArray -> put(key, value)
            null -> putNull(key)
        }
    }
}