package com.android.myapplication.basicknowledge

import com.android.myapplication.myinterface.Study
import com.android.myapplication.bean.CellPhone
import com.android.myapplication.bean.Person
import com.android.myapplication.bean.Singleton
import com.android.myapplication.bean.Student2
import com.android.myapplication.bean.Student22
import com.android.myapplication.bean.Student3
import kotlin.collections.HashMap

/**
 * @author longbin
 * @date 2024/10/14
 */

fun main() {

    //面向对象编程
    val person = Person()
    person.age = 20
    person.name = "XiaoMing"

    person.eat()

    val student = Student2("A001", 5, "male")
    student.name = "Michael"
    student.age = 30
    student.eat()

    //继承：
    val student2 = Student2("A0002", 6, "male")
    val student21 = Student2() //主构造函数的init 代码块先于次构造函数执行

    val student3 = Student3()
    //执行顺序 ，父类init->子类init->子类构造函数
    //Person2 init
    //Students3 init
    // Students3 constructor init

    //接口，
    student3.doHomeWork()
    student3.readBook()
    student3.doHomeWorkMath()

    //数据类 data
    val cellPhone = CellPhone("oppo", 2000.0)
//    cellPhone.brand = "vivo"
    val cellPhone2 = CellPhone("oppo", 2000.0)
    println("cellPhone == cellPhone2 is ${cellPhone == cellPhone2}") //true


    //单例类Object
    Singleton.singletonTest()

    //▲▲▲▲▲Lambda start
    //而Kotlin从第一个版本开始就支持了Lambda编程，并且Kotlin中的Lambda功能极为强大，我
    //甚至认为Lambda才是Kotlin的灵魂所在.Lambda这一节我们只学习一些Lambda编程的基
    //础知识，而像高阶函数、DSL等高级Lambda技巧，我们会在本书的后续章节慢慢学习
    //集合listOf，listOf()函数创建的是一个不可变的集合
    val fruit = listOf("Apple", "banana", "Orange", "pear", "Grape")
    // fruit.add('" ")没有add函数,不可变集合
    for (fruit in fruit) print("$fruit ; ") //集合的遍历
    println()
    //可变集合mutableListOf
    val mFruit = mutableListOf<String>()
    mFruit.add("watermelon")
    mFruit.add("peanut")

    //集合Set,用法与List几乎一致。需要注意，Set集合中是不可以存放重复元素的，如果存放了多个相同的元素，只会保留其中一
    //份，这是和List集合最大的不同之处。
    val fruitSet = setOf("Apple", "Banana", "Orange", "Pear", "Grape")
    val mFruitSet = mutableSetOf("Apple", "Banana", "Orange", "Pear", "Grape")
    mFruitSet.add("Peanut")

    //集合Map
    //写法一，通用写法,使用put
    val map = HashMap<String, Int>()
    map.put("Apple", 1)
    //写法二 ，下标赋值
    map["Banana"] = 2
    //写法三，使用infix函数 'to'
    val map2 = mapOf("Apple" to 1, "Banana" to 2, "Orange" to 3)
    //可变map集合mutableMapOf
    val map22 = mutableMapOf("Apple" to 1, "Banana" to 2, "Orange" to 3, 0.56 to 78)//map中key-value类型可以混合，也可以成功遍历
    map22.put(4,5)
    //遍历Map
    for ((fruit, number) in map22) print("$fruit number is $number ; ")
    println()


    //集合的函数式API
    //重点学习函数式API的语法结构，也就是Lambda表达式的语法结构
    //Lambda的定义，如果用最直白的语言来阐述的话，Lambda就是一小段可以作为参数传递的代码。
    //Lambda表达式的语法结构：
    //{参数名1: 参数类型, 参数名2: 参数类型 -> 函数体}
    //首先最外层是一对大括号，如果有参数传入到
    //Lambda表达式中的话，我们还需要声明参数列表，参数列表的结尾使用一个->符号，表示参
    //数列表的结束以及函数体的开始，函数体中可以编写任意行代码（虽然不建议编写太长的代
    //码），''''并且最后一行代码会自动作为Lambda表达式的返回值''''(划重点)。

    //例如：找出单词最长的水果。maxBy函数的工作原理是根据我们传入的条件来遍历集合，从而找到该条件下的最大值
    val lambda = { fruit: String -> fruit.length }
    val fruitMostLength = fruit.maxByOrNull(lambda)
    println("fruitMostLength fruit is $fruitMostLength")
    //使用语法糖简写版本，Kotlin规定，当Lambda参数是函数的最后一个参数时，可以将Lambda表达式移到函数括号的外面（括号没有参数时可以省略）
    val fruitMostLength2 = fruit.maxByOrNull { fruit: String -> fruit.length }
    //由于Kotlin拥有出色的类型推导机制，Lambda表达式中的参数列表其实在大多数情况下不必声明参数类型
    val fruitMostLength3 = fruit.maxByOrNull { fruit -> fruit.length }
    //最后，当Lambda表达式的参数列表中'只有一个参数时'，也不必声明参数名，而是可以使用it关键字来代替，那么代码就变成了
    val fruitMostLength4 = fruit.maxByOrNull { it.length }
    println("fruitMostLength4 fruit is $fruitMostLength4")

    //集合中的map函数是最常用的一种函数式API,它用于将集合中的每个元素都映射成一个另外的
    //值，映射的规则在Lambda表达式中指定，最终生成一个新的集合
    val fruitNew = fruit.map { it.uppercase() }
    for (fruit in fruitNew) print("$fruit ; ")
    println()
    //map函数的功能非常强大，它可以按照我们的需求对集合中的元素进行任意的映射转换，上面只
    //是一个简单的示例而已。除此之外，你还可以将水果名全部转换成小写，或者是只取单词的首
    //字母，甚至是转换成单词长度这样一个数字集合，只要在Lambda表示式中编写你需要的逻辑即可。

    //另外一个比较常用的函数式API——filter函数
    //比如我们只想保留5个字母以内的水果，就可以借助filter函数来实现( prepend () 是向指定的元素内部插入元素，并且插入位置在开头。
    val fruitFilter = fruit.filter { it.length < 6 }.map { it.prependIndent("☆") }
//    val fruitFilter = fruit.filter { it.length < 6 }.map { it.substring(0,9) }
    val fruitFilterC = fruit.filter { it.length < 6 }.map { it.capitalize() }
    for (fruit in fruitFilter) print("$fruit ; ")
    println()
    for (fruit in fruitFilterC) print("$fruit ; ")
    println()

    //继续学习两个比较常用的函数式API——any和all函数.其中any函数用于判断集
    //合中是否至少存在一个元素满足指定条件，all函数用于判断集合中是否所有元素都满足指定条件。返回boolean值
    val anyResult = fruit.any { it.length < 6 }
    val allResult = fruit.all { it.length < 6 }
    println("anyResult is $anyResult, allResult is $allResult")

    // ‘Java函数式API的使用’ ，如果我们在Kotlin代码中调用了一个
    //Java方法，并且该方法接收一个Java单抽象方法接口参数，就可以使用函数式API。Java单抽象
    //方法接口指的是接口中只有一个待实现方法，如果接口中有多个待实现方法，则无法使用函数式API。
    //例如 runnable接口，view的onclickListener
//    public interface Runnable {
//        void run();
//    }
//    public interface OnClickListener {
//        void onClick(View var1);
//    }
    Thread {
        println("java runnable 函数式api")
    }.start()

    /* val context = Context();
     View(context).setOnClickListener(View.OnClickListener {
         println("view click")
     })
     //简写版
    View(context).setOnClickListener { println("view onclick") }*/

    //▲▲▲▲▲Lambda end


    //空指针检查
    //可空类型 Int?  String?
    //?.  是当对象不为空时正常调用相应的方法，当对象为空时则什么都不做。
    doStudy(student3)

    //再来学习另外一个非常常用的 ?: 操作符
    //这个操作符的左右两边都接收一个表达式，
    //如果左边表达式的结果不为空就返回左边表达式的结果，否则就返回右边表达式的结果
    val a = null
    val b = 3
    val c = 5
    val d = a ?: b
    //val e = b > c?b:c  kotlin中没有三元操作符了，用if else代替使用
    getLength("abc")

    //偶然看见的 高级知识点，mutableListOf 元素类型可以混杂着，不禁感叹高级；forEach遍历方法
    val mutableListOf = mutableListOf("String", false, 1, 'c')
    mutableListOf.forEach() {
        when (it) {
            is String -> println("String")
            is Boolean -> println("Boolean")
            is Char -> println("Char")
            else -> println("Int")
        }
    }


    //Kotlin的空指针检查机制也并非总是那么智能，当content不为空的时候才会调用printUpperCase()函数。
    // 看上去好像逻辑没什么问题，但是很遗憾，这段代码一定是无法运行的
    //如果我们想要强行通过编译，可以使用非空断言工具，写法是在对象的后面加上!!
    if (content != null) {
        printUpperContent()
    }

    //我们再来学习一个比较与众不同的辅助工具——let。let既不是操作符，也不是什么关键字，而是一个函数
    //这个函数提供了函数式API的编程接口，并''将原始调用对象作为参数传递到Lambda表达式中''
//    obj.let { obj2 ->
//        // 编写具体的业务逻辑
//    }
    //实let函数的特性配合?.操作符可以在空指针检查的时候起到很大的作用。
    //优化使用doStudy函数，不用在每个地方都去判空使用了
    doStudy2(student3)
    //我还得再讲一点，let函数是可以处理全局变量的判空问题的，而if
    //判断语句则无法做到这一点。比如我们将doStudy()函数中的参数student3变成一个全局变量，使用
    //let函数仍然可以正常工作，但使用if判断语句则会提示错误


    // "函数的参数默认值" ，学习另外一个非常有用的小技巧——给函数设定参数默认值
    //实之前在学习次构造函数用法的时候我就提到过，次构造函数在Kotlin中很少用，因为Kotlin
    //提供了给函数设定参数默认值的功能，它在很大程度上能够替代次构造函数的作用
    //具体来讲，我们可以在定义函数的时候给任意参数设定一个默认值，这样当调用此函数时就不
    //会强制要求调用方为此参数传值，在没有传值的情况下会自动使用参数的默认值。
    printParams(56)
    printParams(66, "hello,world!")
    //Kotlin提供了另外一种神奇的机制，就是可以通过''键值对的方式来传参''，从而不
    //必像传统写法那样按照参数定义的顺序来传参。
    printParams(str = "hello,kt")
    //回到替代构造函数的作用，例如class Student2省略次构造函数，因为我们完全可以通过只编写一个主构造函数，然后给参
    //数设定默认值的方式来实现
    Student22()
    Student22(sno = "B008", sex = "male")


}

fun doStudy(study: Study?) {
    study?.doHomeWork()  //
    study?.readBook()
}

fun getLength(string: String?) = string?.length ?: 0

var content: String? = "hello"
fun printUpperContent() {
//    if (content != null) { //也编译不过，建议使用let
//        content = content.uppercase() //编译不过
//    }
    content = content!!.uppercase()
    println("printContent $content")
}

fun doStudy2(study: Study?) {
    study?.let {
        study.readBook()
        study.doHomeWork()
        "rtytyr"
    }

    study?.let { it.doHomeWorkMath() }
}


fun printParams(num: Int = 88, str: String = "hello") {
    println("printParams $num , $str")
}