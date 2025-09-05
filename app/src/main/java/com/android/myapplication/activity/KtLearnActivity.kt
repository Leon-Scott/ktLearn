package com.android.myapplication.activity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.myapplication.myinterface.Failure
import com.android.myapplication.R
import com.android.myapplication.myinterface.Result
import com.android.myapplication.myinterface.Success
import com.android.myapplication.basicknowledge.startActivityT
import kotlinx.android.synthetic.main.activity_main.*

class KtLearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //其他零散知识点进阶
        val intent = Intent(this, SecondActivity::class.java)
        //Kotlin中SecondActivity::class.java的写法就相当于Java中SecondActivity.class的写法。

        //findViewById()方法返回的是一个继承自View的泛型对象，因此Kotlin无法自动
        //推导出它是一个Button还是其他控件，所以我们需要将button1变量显式地声明成Button类型。
        val button2: Button = findViewById(R.id.button1)
        val button3 = findViewById<Button>(R.id.button1)
        //此findViewById()方法才行。这种写法虽然很正确，但是很笨拙，于是就滋生出了诸如ButterKnife之类的第三方开源库，来简化
        //findViewById()方法的调用。
        //不过，这个问题在Kotlin中就不复存在了，因为使用Kotlin编写的Android项目在
        //app/build.gradle文件的头部默认引入了一个'kotlin-android-extensions'插件，这个插件会根
        //据布局文件中定义的控件id自动生成一个具有相同名称的变量，我们可以在Activity里直接使用
        //这个变量，而不用再调用findViewById()方法了
        button1.setOnClickListener {
            Toast.makeText(this,"hello, kt!",Toast.LENGTH_SHORT).show()
            println()
        }


        button3.setOnDragListener { v, event ->
            v.refreshDrawableState()
            false
//            return@setOnDragListener false
        }
        button3.setOnDragListener(object :View.OnDragListener{
            override fun onDrag(v: View?, event: DragEvent?): Boolean {
                return true
            }
        })

        button3.setOnDragListener { v, event -> true }


        //java bean的set和get方法，在kotlin中调用时直接使用 . 就行。这是Kotlin给我们提供的语法糖，
        // 它会在背后自动将上述代码转换成调用setPages()方法和getPages()方法。

        //Kotlin中的javaClass表示获取当前实例的Class对象，相当于在Java中调用getClass()方法；
        // 而Kotlin中的BaseActivity::class.java表示获取BaseActivity类的Class对象，相当于在Java中调用BaseActivity.class。
        Log.d("BaseActivity", javaClass.simpleName)



        //实例(举例)优化,使用 apply（返回当前调用对象）
        val intent2 = Intent(this, SecondActivity::class.java)
        intent2.putExtra("param1", "data1")
        intent2.putExtra("param2", "data2")
//        startActivity(intent2)

        val intent3 = Intent(this, SecondActivity::class.java).apply {
            putExtra("age",34)
            putExtra("name","Micheal")
        }


        AlertDialog.Builder(this).apply {
            setTitle("This is Dialog")
            setMessage("Something important.")
            setCancelable(false)
            setPositiveButton("OK") { p0, p1 ->
            }
//            setNegativeButton("Cancel") { dialog, which ->
//            }
            setNegativeButton("Cancel",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    TODO("Not yet implemented")
                }
            })
//            show()
        }

        //强转as,Kotlin中的类型强制转换使用的关键字是as,context强转Activity
        val activity = this as Activity


    }

    override fun onResume() {
        super.onResume()
    }


    //新的语法结构companion object，并在companion object中定义了一个actionStart()方法。
    //之所以要这样写，是因为Kotlin规定，所有定义在companion object代码块中的方法(伴生类，单例模式)
    //都可以使用类似于Java静态方法的形式调用。
    companion object {
        fun actionStart(context: Context, data1: String, data2: String) {
            val intent1 = Intent(context, SecondActivity::class.java)
            val intent = Intent(context, SecondActivity::class.java)//连续打两个冒号后面提示就出来了
            intent.putExtra("param1", data1)
            intent.putExtra("param2", data2)
            context.startActivity(intent)
        }
    }


    //Kotlin中使用inner class关键字来定义内部类。没有inner也不会报编译错误
    inner class ViewHolder(val fruitImage: ImageView, val fruitName: TextView)

    //虽然这里我们必须在Lambda表达式中声明4个参数，但实际上却只用到了
    //position这一个参数而已。针对这种情况，Kotlin允许我们将没有用到的参数使用下划线来替
    //代，因此下面这种写法也是合法且更加推荐的：
    private val listView = ListView(this)
    fun setListener() {
        listView.setOnItemClickListener { parent, view, position, id ->
            println("$position")
        }

        //下划线代替
        listView.setOnItemClickListener { _, _, position, _ ->
            println("$position")
        }

        listView.setOnItemClickListener(object:AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }

        })
    }


    //kt小课堂，延迟初始化和密封类
    //延迟初始化使用的是lateinit关键字，它可以告诉Kotlin编译器，
    //我会在晚些时候对这个变量进行初始化，这样就不用在一开始的时候将它赋值为null了
    private var button:Button? = null
    fun setBtListener(){
        button = button1
        button?.setOnClickListener { println() }  //缺点尽管已经确认在前面处理不会null，还是要加?.处理，编译器才能通过
    }
    //优化后
    lateinit var button2:Button
    fun setBtListener2(){
        button2 = button1
        //另外，我们还可以通过代码来判断一个全局变量是否已经完成了初始化，这样在某些时候能够
        //有效地避免重复对某一个变量进行初始化操作
        //::button2.isInitialized可用于判断button2变量是否已经初始化。
        // 虽然语法看上去有点奇怪，但这是固定的写法。
        if (::button2.isInitialized) {
            button2.setOnClickListener { println() }
        }
    }
    //当然使用lateinit关键字也不是没有任何风险，如果我们在adapter变量还没有初始化的
    //情况下就直接使用它，那么程序就一定会崩溃，并且抛出一个UninitializedPropertyAccessException异常

    //在Kotlin中，lateinit修饰符不能用于基本数据类型的属性,'lateinit' modifier is not allowed on properties of primitive types
    //譬如，如下写法会报错
//    lateinit var remainingTime: Int

    //使用''密封类''优化代码，见result.kt。密封类的关键字是sealed class
    //那么改成密封类之后有什么好处呢？你会发现现在getResultMsg()方法中的else条件已经不再需要了
    fun getResult(result: Result){
        when(result){
            is Success -> println("${result.msg}")
//            is Failure -> throw IllegalArgumentException()
            is Failure -> result.error
        }
    }
    //因为当在when语句中传入一个密封类变量
    //作为条件时，Kotlin编译器会自动检查该密封类有哪些子类，{并强制要求你将每一个子类所对应的条件全部处理}。
    //这样就可以保证，即使没有编写else条件，也不可能会出现漏写条件分支的
    //情况。而如果我们现在新增一个Unknown类，并也让它继承自Result，此时
    //getResultMsg()方法就一定会报错，必须增加一个Unknown的条件分支才能让代码编译通过。
    //这就是密封类主要的作用和使用方法了。
    //另外再多说一句，密封类及其所有子类只能定义在同一个文件的顶层位置，不能嵌套在其他类中，这是被密封类底层的实现机制所限制的。



    //val fragment = supportFragmentManager.findFragmentById(R.id.leftFrag) as LeftFragment
    //kotlin-android-extensions插件也对findFragmentById()方法进行了扩展，允许我们直接使用布局文件中定义的Fragment id名
    //称来自动获取相应的Fragment实例，
//    val fragment = leftFrag as LeftFragment

    //常量，Kotlin中定义常量都是使用的这种方式，在companion object、单例类或顶层作用域中使用const关键字声明一个变量即可
//    companion object {
//        const val TAG = "RightFragment"
//    }

    //泛型的实化 应用
    fun startActivity() {
       // 原来的方式
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("name","loon")
        intent.putExtra("age",18)
        startActivity(intent)
        //实化后。编写实化顶层函数
        startActivityT<SecondActivity>(this) {
            putExtra("name","loon")
            putExtra("age",18)
        }
    }

}
