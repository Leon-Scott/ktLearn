package com.android.myapplication.basicknowledge

import kotlinx.coroutines.*
import java.io.IOException
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author  longbin
 * @date 2024/10/18
 */

fun main(){

    //11 Kotlin课堂：使用协程编写高效的并发程序
    //可以简单地将它理解成一种轻量级的线程。
    //要知道，我们之前所学习的线程是非常重量级的，它需要依靠操作系统的调度才能实现不同线
    //程之间的切换。而使用协程却可以仅在编程语言的层面就能实现不同协程之间的切换，从而大
    //大提升了并发编程的运行效率。

    GlobalScope.launch(Job()+Dispatchers.Main,CoroutineStart.ATOMIC) {
        println("code run in coroutine scope")
    }

    //刚才的日志之所以无法打印出来，就是因为代码块中的代码还没来得及
    //运行，应用程序就结束了。
//    Thread.sleep(1000)
    //这里使用Thread.sleep()方法让主线程阻塞1秒钟，现在重新运行程序，你会发现日志可以
    //正常打印出来了

    //那么有没有什么办法能让应用程序在协程中所有代码都运行完了之后再结束呢？当然也是有
    //的，借助runBlocking函数就可以实现这个功能：
    runBlocking {
        println("code run in coroutine scope")
        delay(1500)
        println("code run in coroutine scope finish")
    }
    //delay()函数是一个非
    //阻塞式的挂起函数，它只会挂起当前协程，并不会影响其他协程的运行。而Thread.sleep()
    //方法会阻塞当前的线程，这样运行在该线程下的所有协程都会被阻塞。
    //runBlocking函数同样会创建一个协程的作用域，但是它可以保证在协程作用域内的所有代码
    //和子协程没有全部执行完之前一直阻塞当前线程。需要注意的是，runBlocking函数通常只应
    //该在测试环境下使用，在正式环境中使用容易产生一些性能上的问题。

    //那么如何才能创建多个协程呢？很简单，使用launch函数就可以了
    runBlocking {
        launch {
            println("launch1")
            delay(1000)
            println("launch1 finish")
        }
        launch {
            println("launch2")
            delay(1000)
            println("launch2 finish")
        }
    }
    //launch1
    //launch2
    //launch1 finish
    //launch2 finish
    //可以看到，两个子协程中的日志是交替打印的，说明它们确实是像多线程那样并发运行的。
    //测试协程的高并发效率。开启十万个协程.这里使用repeat函数循环创建了10万个协程
    val startTime = System.currentTimeMillis()
    runBlocking {
//        repeat(100000) {
        repeat(10) {
            launch {
//                println("$it ;")
                print("$it ;")
            }
        }
        println()
    }
    val endTime = System.currentTimeMillis()
    println("waste time=${endTime - startTime}")
    //99995 ;
    //99996 ;
    //99997 ;
    //99998 ;
    //99999 ;
    //waste time=367
    //可以看到，这里仅仅耗时了367(961毫秒，这足以证明协程有多么高效。试想一下，如果开启的是
    //10万个线程，程序或许已经出现OOM异常了。

    //Kotlin提供了一个suspend关键字，使用它可以将任意函数声明成挂起函数，而挂起函数
    //之间都是可以互相调用的
    //suspend 关键字来表示挂起点，包含了异步调用和回调两层含义。它表示该函数支持同步化的异步调用。
    suspend fun printNumber(num:Number){
        println(num)
        delay(100)//Suspend function 'delay' should be called only from a coroutine or another suspend function
    }
    //但是，suspend关键字只能将一个函数声明成挂起函数，是无法给它提供协程作用域的。比如
    //你现在尝试在printNumber()函数中调用launch函数，一定是无法调用成功的，因为launch函
    //数要求必须在协程作用域当中才能调用。

    //这个问题可以借助coroutineScope函数来解决。coroutineScope函数也是一个挂起函数，
    //因此可以在任何其他挂起函数中调用。它的特点是会继承外部的协程的作用域并创建一个子协
    //程，借助这个特性，我们就可以给任意挂起函数提供协程作用域了。示例写法如下：
    suspend fun printNumber2(number: Number)  = coroutineScope<Int> {
        launch {
            println(number)
            delay(100)
        }
        5
    }

    suspend fun printNumber3(number: Number) = runBlocking {
        launch (Dispatchers.Default){
            println(number)
            delay(100)
        }
    }

    //另外，coroutineScope函数和runBlocking函数还有点类似，它可以保证其作用域内的所
    //有代码和子协程在全部执行完之前，外部的协程会一直被挂起。
    //虽然看上去coroutineScope函数和runBlocking函数的作用是有点类似的，但是
    //coroutineScope函数只会阻塞当前协程，既不影响其他协程，也不影响任何线程，因此是不
    //会造成任何性能上的问题的。而runBlocking函数由于会挂起外部线程，如果你恰好又在主线
    //程中当中调用它的话，那么就有可能会导致界面卡死的情况，所以不太推荐在实际项目中使
    //用。
    //，我们学习了GlobalScope.launch、runBlocking、launch、
    //coroutineScope这几种作用域构建器，它们都可以用于创建一个新的协程作用域。不过
    //GlobalScope.launch和runBlocking函数是可以在任意地方调用的，coroutineScope函
    //数可以在协程作用域或挂起函数中调用，而launch函数只能在协程作用域中调用。
    //那么协程要怎样取消呢？不管是GlobalScope.launch函数还是launch函数，它们都会返回
    //一个Job对象，只需要调用Job对象的cancel()方法就可以取消协程了，如下所示：
    val job = GlobalScope.launch {
     // 处理具体的逻辑
    }
    job.cancel()
    //但是如果我们每次创建的都是顶层协程，那么当Activity关闭时，就需要逐个调用所有已创建协
    //程的cancel()方法，试想一下，这样的代码是不是根本无法维护？
    //因此，GlobalScope.launch这种协程作用域构建器，在实际项目中也是不太常用的。下面我
    //来演示一下实际项目中比较常用的写法：
    val job2:Job = Job()
    val scope = CoroutineScope(job2+Dispatchers.Main+object :CoroutineExceptionHandler{
        override val key: CoroutineContext.Key<*>
            get() = TODO("Not yet implemented")

        override fun handleException(context: CoroutineContext, exception: Throwable) {
            TODO("Not yet implemented")
        }

    }+CoroutineName("lobin"))
    scope.launch {
        println("xxx35")
    }
    job2.cancel()
    //现在所有调用CoroutineScope的launch函数所创建的协程，都会被关联在Job对象的作用域
    //下面。这样只需要调用一次cancel()方法，就可以将同一作用域内的所有协程全部取消，从而
    //大大降低了协程管理的成本。


    //协程的内容确实比较多，下面我们还要继续学习。你已经知道了调用launch函数可以创建一个
    //新的协程，但是launch函数只能用于执行一段逻辑，却不能获取执行的结果，因为它的返回值
    //永远是一个Job对象。那么有没有什么办法能够创建一个协程并获取它的执行结果呢？当然有，
    //使用async函数就可以实现。
    //async函数必须在协程作用域当中才能调用，它会创建一个新的子协程并返回一个Deferred对
    //象，如果我们想要获取async函数代码块的执行结果，只需要调用Deferred对象的await()
    //方法即可，代码如下所示：
    val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        when (throwable) {
            is IOException -> println("网络异常: ${throwable.message}")
            else -> println("其他异常: ${throwable.message}")
        }
        context.job.cancel()
    }
    runBlocking {
        val result = async(Job() + Dispatchers.Default + object : CoroutineExceptionHandler {
            override val key: CoroutineContext.Key<*>
                get() = TODO("Not yet implemented")

            override fun handleException(context: CoroutineContext, exception: Throwable) {
                TODO("Not yet implemented")
            }

        },) {
//            val result = async (Dispatchers.Default){
//        val result = withContext(Dispatchers.Default) {
            5 + 5
        }.await()
        println("async result = $result")
    }
    //不过async函数的奥秘还不止于此。事实上，在调用了async函数之后，代码块中的代码就会
    //立刻开始执行。当调用await()方法时，如果代码块中的代码还没执行完，那么await()方法
    //会将当前协程阻塞住，直到可以获得async函数的执行结果。说明这里的两个async函数确实是一种串行的关
    //系，前一个执行完了后一个才能执行。
    //这种写法明显是非常低效的，因为两个async函数完全可以同时执行从而提高运行效率。
    //现在我们不在每次调用async函数之后就立刻使用await()方法获取结果了，而是仅在需要用
    //到async函数的执行结果时才调用await()方法进行获取，这样两个async函数就变成一种并
    //行关系了。
    runBlocking { testCoroutine() }

    //withContext()函数。withContext()
    //函数是一个挂起函数，大体可以将它理解成async函数的一种简化版写法，示例写法如下：
    runBlocking {
        val result = withContext(Dispatchers.Default) {
            5 + 5
        }
        println(result)
    }
    //我来解释一下这段代码。调用withContext()函数之后，会立即执行代码块中的代码，同时将
    //外部协程挂起。当代码块中的代码全部执行完之后，会将最后一行的执行结果作为
    //withContext()函数的返回值返回，因此基本上相当于val result = async{ 5 + 5
    //}.await()的写法。唯一不同的是，withContext()函数强制要求我们指定一个线程参数，
    //关于这个参数我准备好好讲一讲。
    //你已经知道，协程是一种轻量级的线程的概念，因此很多传统编程情况下需要开启多线程执行
    //的并发任务，现在只需要在一个线程下开启多个协程来执行就可以了。但是这并不意味着我们
    //就永远不需要开启线程了，比如说Android中要求网络请求必须在子线程中进行，即使你开启了
    //协程去执行网络请求，假如它是主线程当中的协程，那么程序仍然会出错。这个时候我们就应
    //该通过线程参数给协程指定一个具体的运行线程。
    //线程参数主要有以下3种值可选：Dispatchers.Default、Dispatchers.IO和
    //Dispatchers.Main。Dispatchers.Default表示会使用一种默认低并发的线程策略，当
    //你要执行的代码属于计算密集型任务时，开启过高的并发反而可能会影响任务的运行效率，此
    //时就可以使用Dispatchers.Default。Dispatchers.IO表示会使用一种较高并发的线程策
    //略，当你要执行的代码大多数时间是在阻塞和等待中，比如说执行网络请求时，为了能够支持
    //更高的并发数量，此时就可以使用Dispatchers.IO。Dispatchers.Main则表示不会开启
    //子线程，而是在Android主线程中执行代码，但是这个值只能在Android项目中使用，纯Kotlin
    //程序使用这种类型的线程参数会出现错误。
    //事实上，在我们刚才所学的协程作用域构建器中，除了coroutineScope函数之外，其他所有
    //的函数都是可以指定这样一个线程参数的，只不过withContext()函数是强制要求指定的，而
    //其他函数则是可选的。
    //到目前为止，你已经掌握了协程中最常用的一些用法，并且了解了协程的主要用途就是可以大
    //幅度地提升并发编程的运行效率。但实际上，Kotlin中的协程还可以对传统回调的写法进行优
    //化，从而让代码变得更加简洁，那么接下来我们就开始学习这部分的内容。
    //使用协程简化回调的写法
    //常用写法：
//    HttpUtil.sendHttpRequest(address, object : HttpCallbackListener {
//        override fun onFinish(response: String) {
//            // 得到服务器返回的具体内容
//        }
//
//        override fun onError(e: Exception) {
//            // 在这里对异常情况进行处理
//        }
//    })
    //优化后写法
    //在多少个地方发起网络请求，就需要编写多少次这样的匿名类实现。这不禁引起了我们的思
    //考，有没有更加简单一点的写法呢？
    //在过去，可能确实没有什么更加简单的写法了。不过现在，Kotlin的协程使我们的这种设想成为
    //了可能，只需要借助suspendCoroutine函数就能将传统回调机制的写法大幅简化，下面我们
    //就来具体学习一下。
    //suspendCoroutine函数必须在协程作用域或挂起函数中才能调用，它接收一个Lambda表达
    //式参数，主要作用是将当前协程立即挂起，然后在一个普通的线程中执行Lambda表达式中的
    //代码。Lambda表达式的参数列表上会传入一个Continuation参数，调用它的resume()方
    //法或resumeWithException()可以让协程恢复执行。
    //了解了suspendCoroutine函数的作用之后，接下来我们就可以借助这个函数来对传统的回调
    //写法进行优化。首先定义一个request()函数，代码如下所示：
    runBlocking (){
        try {
//            val result = request("")
        } catch (e: Exception) {
        }
    }
    //更多用法可以探究泛型规律。书本有retrofit的举例。


    //todo 12 Kotlin课堂：编写好用的工具方法
    /**求多个参数Number类型的最大数*/
    //知识点，Java中Comparable接口的对象都是可以比较的. vararg 接收任意多个参数
    var maxResult = max(2f, 4.5f, 69f,70F,5F)

    println("maxResult = $maxResult")

    //todo 简化Toast的用法 待实践在activity中
    //todo  简化Snackbar的用法  待实践在activity中

    //13 jetPack knowledge
    //ViewModel还有一个非常重要的特性。我们都知道，当手机发生横竖屏旋转的时候，
    //Activity会被重新创建，同时存放在Activity中的数据也会丢失。而ViewModel的生命周期和
    //Activity不同，它可以保证在手机屏幕发生旋转的时候不会被重新创建，只有当Activity退出的
    //时候才会跟着Activity一起销毁。因此，将与界面相关的变量存放在ViewModel当中，这样即
    //使旋转手机屏幕，界面上显示的数据也不会丢失。
    //我们绝对不可以直接去创建ViewModel的实例，而是一>)定要通过ViewModelProvider来获取ViewModel的实例
    //    //ViewModelProvider(<你的Activity或Fragment实例.get(<你的ViewModel>::class.java)
//    viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
}



/*suspend fun request(address:String) :String{
    return suspendCoroutine {
        HttpUtil.sendHttpRequest(address, object : HttpCallbackListener {
            override fun onFinish(response: String) {
                // 得到服务器返回的具体内容
                it.resume(response)
//                it.resume(5)
            }

            override fun onError(e: Exception) {
                // 在这里对异常情况进行处理
                it.resumeWithException(e)
            }
        })
    }
}*/

interface NetworkCallback {
    fun onSuccess(result: String)
    fun onFailure(exception: Throwable)
}

fun makeNetworkRequest(url: String, callback: NetworkCallback) {
    // 模拟网络请求，这里简化为一个延迟操作并随机返回成功或失败
    Thread.sleep(1000)
    if (Math.random() < 0.5) {
        callback.onSuccess("Data from network")
    } else {
        callback.onFailure(java.lang.Exception("Network error"))
    }
}

suspend fun networkRequestCoroutine(url: String): String {
    return withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            makeNetworkRequest(url, object : NetworkCallback {
                override fun onSuccess(result: String) {
                    TODO("Not yet implemented")
                    continuation.resume(result)
                }

                override fun onFailure(exception: Throwable) {
                    TODO("Not yet implemented")
                    continuation.resumeWithException(exception)
                }
            })
        }
    }
}

suspend fun testCoroutine() = coroutineScope(){
    val startTime = System.currentTimeMillis()
    val deferred1 = async (Dispatchers.Default){
        delay(1000)
        5+2
    }
    val deferred2 = async {
        delay(1000)
        5+3
    }
    println("result = ${deferred1.await()+deferred2.await()}")
    val endTime = System.currentTimeMillis()
    println("wasteTime = ${endTime-startTime}")

}

fun <T : Comparable<T>> max(vararg num: T) :T{
    if(num.isEmpty()) throw RuntimeException("Params con not be empty!!!")
    var max = num[0]
    for (num in num) {
        max = if (num > max) num else max
    }
    return max
}