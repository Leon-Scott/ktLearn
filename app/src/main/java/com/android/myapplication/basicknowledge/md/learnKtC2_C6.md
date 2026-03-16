知识点：
一共十六个章节：均分熟悉：一天三~四个章节，五天finish

第一章：无

**第二章：**
1、var,val :variable,value
2、kotlin自带变量类型推导机制
3、Kotlin完全抛弃了Java中的 基本数据类型，全部使用了对象数据类型。举例：无int,boolean,使用Int,Boolean
4、函数的定义：fun methodName(param1:Int,param2:int):Int{}
函数缩写’语法糖‘推导步骤：learnKtC2:line45~65:largeNumber(keyWord)
5、条件语句if和when
Kotlin中的if语句相比于Java有一个额外的功能，它是可以有返回值的，返回值就是if语句每一个条件中最后一行代码的返回值。
```kotlin
import com.android.myapplication.activity.KtLearnActivity;
fun largerNumber(num1: Int, num2: Int): Int {
    val value = if (num1 > num2) {
        num1
    } else {
        num2
    }
    return value
}
KtLearnActivity //引用不到
//缩写：= 等号表达返回值
fun largerNumber(num1: Int, num2: Int) = if (num1 > num2) num1 else num2
```
```java
import com.android.myapplication.activity.KtLearnActivity;

KtLearnActivity.ViewHolder viewHolder = new KtLearnActivity().ViewHolder;
```
6、when条件语句 
类似 switch,但比它强大
when语句和if语句一样， 也是可以有返回值的，因此我们仍然可以使用单行代码函数的语法糖
除了精确匹配之外，when语句还允许进行类型匹配：is (≈ instanceOf,java)
实when语句还有一种不带参数的用法，虽然这种用法可能不太常用，但有的时候却能发挥很强的扩展性。
learnKtC2:line67~101:getScoreWhen(keyWord)

7、循环语句
while循环：while循环不管是在语法还是使用技巧上都和Java中的while循环没有任何区别
for循环：
for in:val range = 0..10[闭区间] 0 until 10[左闭右开区间)
```kotlin
val range = 0..10
for (i in range) {
 println(i)
 }
```
step关键字：step 1:i++  step 2：i+2
```kotlin
val range = 0..10
for (i in range step 2){}
```
downTo关键字：降序 val range = 10 downTo 1 [闭区间]

8、类，继承与构造函数
open class才可以被继承:
主构造函数和次构造函数
class Student:Person(){} ,()这里括号的作用：子类中的构造函数必须调用父类中的构造函数，这个规定在Kotlin中也要遵守。
那么回头看一下Student类，现在我们声明了一个主构造函数，根据继承特性的规定，子类的
构造函数必须调用父类的构造函数，可是主构造函数并没有函数体，我们怎样去调用父类的构造函数呢？
在这里，Person类后面的一对空括号表示Student类的主构造函数在初始化的时候会调用 Person类的无参数构造函数，即使在无参数的情况下，这对括号也不能省略。
---
主构造函数将会是你最常用的构造函数，每个类默认都会有一个不带参数的主构造函数，当然
你也可以显式地给它指明参数。**主构造函数的特点是没有函数体，直接定义在类名的后面即可**
---
主构造函数没有函数体，Kotlin给我们提供了一个init结构体，所有主构造函数中的逻辑都可以写在里面
我们在Student类的主构造函数中增加name和age这两个字段时，不能再将它们声明成 val，
因为在主构造函数中声明成val或者var的参数将自动成为该类的字段，这就会导致和父 类中同名的name和age字段造成冲突。
因此，这里的name和age参数前面我们不用加任何关键 字，让它的作用域仅限定在主构造函数当中即可
```kotlin
class Student(val sno: String, val grade: Int, name: String, age: Int) : Person(name, age) {
 
}
```
次构造函数 constructor
Kotlin规定，当一个类既有主构造函数又有次构造函数时，所有的次构造函数都必须调用主构造 函数（包括间接调用）

```kotlin
class Student(val sno: String, val grade: Int, name: String, age: Int) : Person(name, age) {
    constructor(name: String, age: Int) : this("", 0, name, age) {
    }
    constructor() : this("", 0) {
    }
}
```
一种非常特殊的情况：类中只有次构造函数，没有主构造函数。这种
情况真的十分少见，但在Kotlin中是允许的。当一个类没有显式地定义主构造函数且定义了次构
造函数时，它就是没有主构造函数的。我们结合代码来看一下：
```kotlin
class Student : Person {
    constructor(name: String, age: Int) : super(name, age) {
    }
}
```

9、接口interface : 
多个 继承和实现 用,连接
Kotlin还增加了一个额外的功能：允许对接口中定义的函数进行默认实现，默认实现的函数 实现类中可以选择实现

10、函数的可见性修饰符
分别是public、private、protected(当前类，子类)和internal(同模块)
kotlin public是默认项

11、数据类
data class book(val name:String,val price:Float) 
神奇的地方就在于data这个关键字，当在一个类前 面声明了data关键字时，就表明你希望这个类是一个数据类，
Kotlin会根据主构造函数中的参 数帮你将equals()、hashCode()、toString()等固定且无实际逻辑意义的方法自动生成，
从而大大减少了开发的工作量。

12、单例类
Object Singleton{
    fun test()=1
}
伴生对象方式

13、Lambda
高级用法:高阶函数，DSL（domain-special language）
listOf
mutableListOf
遍历：for (fruit in fruitList) {}
setOf
mutableSetOf
不同点：Set元素不可以重复，List可以存放多个相同元素
Map:
赋值map[key]=value ,取值var value = map[key]
mapOf(key to value,key2 to value2) //to 是infix函数.一种语法糖，使用有限制
for in 遍历map:for ((key,value) in map){}

14、集合的函数式API
list.maxBy{it.length}//fruitList,maxBy根据我们传入的条件遍历，找到最大值
①：val lambda = {fruit:String -> fruit.length}//[Lambda表达式中的最后一行代码会自动作为返回值]
list.maxBy(lambda)
②：如果lambda是函数的最后一个参数可以移到括号外面
list.maxBy(){fruit:String -> fruit.length}
③：出色的类型推导机制省略参数类型声明+[当Lambda中只有一个参数时，可以不必声明，用it代替]
list.maxBy(){it.length}
④：当lambda是唯一参数时，可以省略括号
list.maxBy{it.length}

list.mapOf{}:将集合中的元素根据lambda表达式映射成另外一个值
list.filter{}:根据传入条件过滤集合中的数据
list.any{}:判断是否至少有一个元素满足条件
list.all{}:判断是否所有元素都满足条件e

15、java的函数式API使用
Java的单抽象方法接口可以使用函数式API:Runnable;OnClickListener;
~kotlin没有new关键字，匿名类使用object方式：object:className{}
```java
new Thread(new Runnable(){
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}).start();
```

```kotlin
Thread(object : Runnable {
    override fun run() {
        println("Thread is running")
    }
}).start()
```
Runnable中只有一个待实现方法，不用显示重写。kotlin也能明白Runnable后面的表达式就是要实现的内容。
只有一个单接口参数，接口名可以省略
只有一个Lambda可以放括号外面，并省略括号
```kotlin
Thread{
    println("Thread is running")
}.start()
```

16、空指针检查
可空类型 Int?,String?
?. 操作符，对象为空时则什么都不做
?: 操作符，左边为空则返回右边，否则返回左边
!! 操作符，非空断言。不用帮我检查空指针。我确认它不为空
辅助工具
函数
let:标准函数：obj?.let{it.toString} 立即执行，防空指针好工具,返回最后一行

17、字符串内嵌
print("hello,${obj.name},nice to meet u")

18、函数参数的默认值
```kotlin
fun printParam(age:Int = 18,name:String){
    println("$name 's age is $age")
}

printParam(name = "Tom")
```
这里age函数参数设置了默认值，在调用的时候可以不传此参数。结合构造函数来看，此功能可以很好的替代次构造函数的功能。



**第三章：**
1、标准函数(Standard.kt)**let,with,run,apply,also**用法和区别；
with(obj){obj上下文，这里可以直接调用obj的方法}//obj=stringBuilder,{append("xxx")},返回值是最后一行toString
```kotlin
//吃数字
with(StringBuilder()){
    for (i in 0..10) {
        append(i.toString()).append("\n")
    }
    toString()
}
```
~str.toIntOrNull()  // stringToInt安全转换
run函数：obj.run{obj上下文}，最后一行返回值
apply函数：obj.apply{obj上下文,this.xxx}，无法指定返回值，返回obj本身
also函数：obj.also{it.xxx}，无法指定返回值，返回obj本身

2、定义静态方法：
A:单例类实现方式：1:Object Util，2:Util{ companion object {}}
B:注解：@JvmStatic (只能添加在_和_的方法上);顶层方法:xx.kt(java中引用用文件名xx.fun来使用)

**第四章：**
1、延迟初始化:lateinit;  ::value.isInitialized，作用和用途
2、密封类：sealed class作用和用途，以及限制。配合when条件语句使用，可以省略else，但必须全部子类覆盖。

**第五章：**
1、扩展函数，举例 String的扩展函数，文件类，顶层方法；
```kotlin
fun Classname.methodname(param1:String):Int{return 0}
fun String.letterCount():Int{//this就是扩展对象本身，可在方法里被使用
    var count = 0
    for (char in this){//??这就可以遍历字符串了..
        if (char.isLetter()) count++
    } 
    return count
}
```
2、运算符重载 operator
+ - * / % ++ -- (plus,minus,times,div,rem,inc,dec) 重载方法名是固定的,方法名对应相应的运算符号。...
```kotlin
class Money(val value:Int){
    operator fun plus(money:Money):Money{
        val sum = value+money.value
        return Money(sum)
    }
}

val money1 = Money(5)
money1 + Money(6)//如果没有上面plus 重载方法，+ 在这里无法使用

```


**第六章**
1、高阶函数
函数类型(这里当做变量类型来理解) 语法格式：(String,Int) -> Unit 
~ val lambda = { fruit: String -> fruit.length }
```kotlin
fun highFun(func:(String,Int) -> Int){
    val a:Int = func("aaa",3)
}
```
作用：很广泛。将函数/lambda当做参数传入高阶函数，得到不同的运行结果
```kotlin
fun plus(num:Int,num2:Int):Int {return num + num2 }
fun minus(num:Int,num2:Int):Int {return num - num2 }
fun highFun(num:Int,num2:Int,func:(Int,Int)->Int){
    var result = func(num1,num2)
}
highFun(3,4,::plus)//result = 7
highFun(3,4,::minus)//result = -1
//lambda写法
highFun(3, 4) { num1: Int, num2: Int -> 
    num1 + 1
    num2++ //Val cannot be reassigned,为啥默认val。Kotlin 函数参数（包括高阶函数中的函数类型参数）默认是 val，不可重新赋值
    num1 + num2 }//Lambda表达式中的最后一行代码会自动作为返回值
```
::plus 函数引用方式的固定写法

进一步探究 高阶函数
```kotlin
fun StringBuilder.build(block:StringBuilder.()->Unit):StringBuilder{
    block()
    return this
}

StringBuilder().build { 
    append("123")
    /3
}
//单纯表示block是定义在StringBuilder类当中的,build也是此类当中，所以可以理解接受了一个对应的扩展函数，也是一个限制。某种理解也是写法上的便捷。
////
fun StringBuilder.build2(block:(StringBuilder)->Unit):StringBuilder{
    block(this)
    return this
}

StringBuilder().build2 {
    it.append("123")
    append("123")
}

///
fun StringBuilder.build3(block:Int.()->Unit):StringBuilder{
    3.block()// ❌3.block()这种写法试图调用 Int 类型的扩展函数，但 Kotlin 中基本类型字面量不能直接调用扩展函数。
    (3).block()  // ✅ 正确写法，需要括号
    return this
}

StringBuilder().build3 {
    +1
    plus(1)
    println()
}
```
解析：包含知识点 扩展函数，[函数类型前加了StringBuilder.则可以在函数类型里自动拥有StringBuilder的上下文].类似于标准函数apply
只不过apply可以用于所有对象，此处build只能用于StringBuilder。apply为何如此高级，因为需要借助于Kotlin的泛型才行。后续学习。

2、内联函数
它的作用和产生背景：kotlin代码都会转换成Java字节码，高阶函数的用法每次被调用都会产生一个匿名类实例，造成额外的开销。
```kotlin
highFun(3,4){num1:Int,num2:Int-> num1 + num2}//Lambda表达式中的最后一行代码会自动作为返回值
```
```java
interface Function{
    public Integer invoke(Integer n1, Integer n2);
}

//上述kt转换java
void highFun(int num1,int num2,Function operation){
    operation.invoke(num1,num2);
}
    
void main(){
    highFun(3,4,new Function(){
        @Override
        public Integer invoke(Integer n1, Integer n2) {
            return n1 + n2;
        }
    });
}
```
内联函数用于解除上面匿名类的开销消极影响。
用法：高阶函数前加关键字inline
```kotlin
inline fun highFunInline(num1:Int,num2:Int,funxx:(Int,Int)->Int){
    var result = funxx(num1,num2)
}

```
内联函数的工作原理又是什么呢？其实并不复杂，就是Kotlin编译器会将内联函数中的代码
在编译的时候自动替换到调用它的地方，这样也就不存在运行时的开销了。

3、crossinline和noinline
一个高阶函数中如果接收了两个或者更多函数
类型的参数，这时我们给函数加上了inline关键字，那么Kotlin编译器会自动将所有引用的
Lambda表达式全部进行内联。
但是，如果我们只想内联其中的一个Lambda表达式该怎么办呢？这时就可以使用noinline关
键字了。
那么为什么Kotlin还要提供一个noinline关键字来排除
内联功能呢？这是因为内联的函数类型参数在编译的时候会被进行代码替换，因此它没有真正
的参数属性。非内联的函数类型参数可以自由地传递给其他任何函数，因为它就是一个真实的
参数，而内联的函数类型参数只允许传递给另外一个内联函数，这也是它最大的局限性。
---
Lambda表达式中是不允许直接使用return关键字的，这里使用了return@printString的写法，表示进行局部返回，
并且不再执行Lambda表达式的剩余部分代码。
```kotlin
fun printString(str:String,block:(String)->Unit){
    println("printString start")
    block(str)
    println("printString end")
}

fun main(){
    println("main start")
    val str = ""
    printString(str){str -> 
        if (str.isEmpty()) return@printString
        println(str)
    }
    println("main end")
}
//return@printString的写法，表示进行局部返回.("main end")还会继续打印

//现在printString2()函数变成了内联函数，我们就可以在Lambda表达式中使用return关键字了。此时的return代表的是返回外层的调用函数，
// 也就是main2()函数，
inline fun printString2(str:String,block:(String)->Unit){
    println("printString start")
    block(str)
    println("printString end")
}

fun main2(){
    println("main start")
    val str = ""
    printString2(str){str ->
        if (str.isEmpty()) return
        println(str)
    }
    println("main end")
}
```
将高阶函数声明成内联函数是一种良好的编程习惯，事实上，绝大多数高阶函数是可以直接声明成内联函数的，但是也有少部分例外的情况。
crossline,在匿名类中调用内联函数会提示不允许。因为此时是不可能调用外层函数返回的。也就是return关键字。
crossline关键字可以解决这个问题。
```kotlin
inline fun highFun( crossinline block:()->Unit){
    var runnable = Runnable{
        block()
    }.run()
}
```
因为内联函数的Lambda表达式中允许使用return关键字，和高阶函数的匿名类实现中不允许使用return关键字之间造成了冲突。
而crossinline关键字就像一个契约，它用于保证在内联函数的Lambda表达式中一定不会使用return关键字，这样冲突就不存在了，问题也就巧妙地解决了。
但是仍然可以使用return@runRunnable的写法进行局部返回。总体来说，除了在return关键字的使用上有所区别之外，crossinline保留了内联函数的其他所有特性。




