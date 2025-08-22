package com.android.myapplication.bean

import com.android.myapplication.myinterface.Study

/**
 * @author  longbin
 * @date 2024/10/14
 */
class Student3: Person2, Study {

    init {
        println("Students3 init")
    }

    //类中只有次构造函数，没有主构造函数。也是允许的。
    constructor():super(){
//    constructor(){ //效果同上一样
        println("Students3 constructor init")
    }

    override fun readBook() {
        println("students3 read book")
    }

    override fun doHomeWork() {
        println("students3 do homework")
    }
}