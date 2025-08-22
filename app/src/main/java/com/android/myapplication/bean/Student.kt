package com.android.myapplication.bean

/**
 * @author  longbin
 * @date 2024/10/14
 */
class Student: Person() { //每个类默认都会有一个不带参数的主构造函数，
    var sno = ""
    var grade = 0


}


//当然你也可以显式地给它指明参数。主构造函数的特点是没有函数体，直接定义在类名的后面
//Person类后面的一对空括号表示Student类的主构造函数在初始化的时候会调用 Person类的无参数构造函数，即使在无参数的情况下，这对括号也不能省略。
//我们在Student类的主构造函数中增加name和age这两个字段时(sex)，不能再将它们声明成 val，因为在主构造函数中声明成val或者var的参数将自动成为该类的字段，
    // ->这就会导致和父 类中同名的name和age字段造成冲突。因此，这里的sex参数前面我们不用加任何关键 字，让它的作用域仅限定在主构造函数当中即可
class Student2(val sno: String, val grade: Int,  sex: String) : Person(sex) {

    //kt中fun默认都是public的，对所有类可见，还有protected(只对当前类和子类可见),internal(只对同一模块中的类可见),private(私有)
    override fun eat() {
        println("$name sno is $sno,grade is $grade,$age years old,sex is $sex")
    }

    //Kotlin给我们提供了一个init结构体，所有主构造函数中的逻辑都可以写在里面
    init {
        println("sno.length= ${sno.length}")
        println("stu.age= $age")
    }

    //主构造函数的init 代码块先于次构造函数的函数体执行
    constructor() :this("A00004",3,"top") {
        println("student2 次构造函数")
    }
}

//数据类，神奇的地方就在于data这个关键字，当在一个类前
//面声明了‘’data‘’关键字时，就表明你希望这个类是一个数据类，Kotlin会根据主构造函数中的参
//数帮你将equals()、hashCode()、toString()等固定且无实际逻辑意义的方法自动生成，
//从而大大减少了开发的工作量。
data class CellPhone(var brand: String, val price: Double)

//单例类object （匿名类的声明也用object
object Singleton{
     fun singletonTest(){
         println("Singleton is called")
     }
}

class Student22(val sno: String = "A001", val grade: Int = 3, sex: String = "none") : Person(sex) {

}


