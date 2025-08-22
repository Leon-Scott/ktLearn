package com.android.myapplication.myinterface

import java.lang.Exception

/**
 * @author  longbin
 * @date 2024/10/15
 */

//密封类的关键字是sealed class
sealed class Result
class Success(val msg:String): Result()
class Failure(val error:Exception): Result()