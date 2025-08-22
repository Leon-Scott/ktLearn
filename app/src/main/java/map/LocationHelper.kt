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
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.*
import kotlin.coroutines.resume


object LocationHelper {

    fun getLocationServiceStatus(context: Context):Boolean{
        return (context.getSystemService(LOCATION_SERVICE) as LocationManager)
            .isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    */
/**
     * 打开定位服务设置
     *//*

    fun openLocationSetting(context: Context):Boolean{
        return try {
            val settingsIntent = Intent()
            settingsIntent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
            settingsIntent.addCategory(Intent.CATEGORY_DEFAULT)
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            context.startActivity(settingsIntent)
            true
        } catch (ex: java.lang.Exception) {
            false
        }
    }

    */
/**
     * 获取当前定位
     *//*

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    suspend fun getLocation(context: Activity,timeOut: Long = 2000):Location{
        val location = getLocationByFusedLocationProviderClient(context)
        //默认使用FusedLocationProviderClient 如果FusedLocationProviderClient不可用或获取失败，则使用LocationManager进行二次获取
        Log.d("LocationHelper", "getLocation:$location")
        return  if (location.latitude == 0.0){
            getLocationByLocationManager(context, timeOut)
        }else{
            location
        }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    private suspend fun getLocationByLocationManager(context: Activity,timeOut: Long = 2000):Location{
        Log.d("LocationHelper", "getLocationByLocationManager")
        val locationManager =  context.getSystemService(LOCATION_SERVICE) as LocationManager
        //检查LocationManager是否可用
        return  if (LocationManagerUtils.checkLocationManagerAvailable(locationManager)){
            //使用LocationManager获取当前经纬度
            val location = LocationManagerUtils.getCurrentPosition(locationManager, timeOut)
            if (location.latitude == 0.0){
                LocationManagerUtils.getLastLocation(locationManager)
            }else{
                location
            }
        }else{
            //获取失败，则采用默认经纬度
            LocationManagerUtils.createDefaultLocation()
        }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    private suspend fun getLocationByFusedLocationProviderClient(context: Activity):Location{
        Log.d("LocationHelper", "getLocationByFusedLocationProviderClient")
        //使用FusedLocationProviderClient进行定位
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        return if (FusedLocationProviderUtils.checkFusedLocationProviderAvailable(fusedLocationClient)){
            withContext(Dispatchers.IO){
                //使用FusedLocationProviderClient获取当前经纬度
                val location = FusedLocationProviderUtils.getCurrentPosition(fusedLocationClient)
                if (location.latitude == 0.0){
                    FusedLocationProviderUtils.getLastLocation(fusedLocationClient)
                }else{
                    location
                }
            }
        }else{
            LocationManagerUtils.createDefaultLocation()
        }
    }
}*/
