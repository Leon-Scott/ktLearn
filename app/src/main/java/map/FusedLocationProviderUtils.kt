/*
package map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.*
import kotlin.coroutines.resume

object FusedLocationProviderUtils {

    val TAG = "FusedLocationUtils"

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    suspend  fun checkFusedLocationProviderAvailable(fusedLocationClient: FusedLocationProviderClient):Boolean{
        return  try {
            withTimeout(1000){
                suspendCancellableCoroutine { continuation ->
                    fusedLocationClient.locationAvailability.addOnFailureListener {
                        Log.d(TAG, "locationAvailability:addOnFailureListener:${it.message}")
                        if (continuation.isActive){
                            continuation.resume(false)
                        }
                    }.addOnSuccessListener {
                        Log.d(TAG, "locationAvailability:addOnSuccessListener:${it.isLocationAvailable}")
                        if (continuation.isActive){
                            continuation.resume(it.isLocationAvailable)
                        }
                    }
                }
            }
        }catch (e:Exception){
            return false
        }
    }

    ///获取最后已知的定位信息
    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    suspend fun getLastLocation(fusedLocationClient: FusedLocationProviderClient):Location{
        return  suspendCancellableCoroutine {continuation ->
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (continuation.isActive){
                    Log.d(TAG, "current location success:$it")
                    if (it != null){
                        continuation.resume(it)
                    }else{
                        continuation.resume(createDefaultLocation())
                    }
                }
            }.addOnFailureListener {
                continuation.resume(createDefaultLocation())
            }
        }
    }

    */
/**
     * 获取当前定位，需要申请定位权限
     *
     *//*

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    suspend fun getCurrentPosition(fusedLocationClient: FusedLocationProviderClient): Location {
        return suspendCancellableCoroutine {continuation ->
            fusedLocationClient.getCurrentLocation(createLocationRequest(),object : CancellationToken(){
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                    return CancellationTokenSource().token
                }

                override fun isCancellationRequested(): Boolean {
                    return false
                }
            }).addOnSuccessListener {
                if (continuation.isActive){
                    Log.d(TAG, "current location success:$it")
                    if (it != null){
                        continuation.resume(it)
                    }else{
                        continuation.resume(createDefaultLocation())
                    }
                }
            }.addOnFailureListener {
                Log.d(TAG, "current location fail:$it")
                if (continuation.isActive){
                    continuation.resume(createDefaultLocation())
                }
            }.addOnCanceledListener {
                Log.d(TAG, "current location cancel:")
                if (continuation.isActive){
                    continuation.resume(createDefaultLocation())
                }
            }
        }
    }

    //创建当前LocationRequest对象
    private fun createLocationRequest():CurrentLocationRequest{
        return CurrentLocationRequest.Builder()
            .setDurationMillis(1000)
            .setMaxUpdateAgeMillis(5000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
    }

    //创建默认值
    private fun createDefaultLocation():Location{
        val location = Location("network")
        location.longitude = 0.0
        location.latitude = 0.0
        return location
    }
}*/
