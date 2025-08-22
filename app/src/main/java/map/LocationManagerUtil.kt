package map

import android.Manifest.permission
import android.location.*
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.resume


object LocationManagerUtils {
    val TAG = "LocationManagerUtils"

    /**
     * @mLocationManager 传入LocationManager对象
     * @minDistance  位置变化最小距离：当位置距离变化超过此值时，将更新位置信息（单位：米）
     * @timeOut 超时时间，如果超时未返回，则直接使用默认值
     */
    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    suspend  fun getCurrentPosition(
        mLocationManager: LocationManager,
        timeOut: Long = 3000,
    ):Location{
        var locationListener : LocationListener?=null
        return  try {
            //超时未返回则直接获取失败，返回默认值
            withTimeout(timeOut){
                suspendCancellableCoroutine {continuation ->
                    //获取最佳定位方式，如果获取不到则默认采用网络定位。
                    var bestProvider = mLocationManager.getBestProvider(createCriteria(),true)
                    if (bestProvider.isNullOrEmpty()||bestProvider == "passive"){
                        bestProvider = "network"
                    }
                    Log.d(TAG, "getCurrentPosition:bestProvider:${bestProvider}")
                    locationListener = object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            Log.d(TAG, "getCurrentPosition:onCompete:${location.latitude},${location.longitude}")
                            if (continuation.isActive){
                                continuation.resume(location)
                                mLocationManager.removeUpdates(this)
                            }
                        }
                        override fun onProviderDisabled(provider: String) {
                        }

                        override fun onProviderEnabled(provider: String) {
                        }
                    }
                    //开始定位
                    mLocationManager.requestLocationUpdates(bestProvider,
                        1000,0f,
                        locationListener!!)
                }
            }
        }catch (e:Exception){
            try {
                locationListener?.let {
                    mLocationManager.removeUpdates(it)
                }
            }catch (e:Exception){
                Log.d(TAG, "getCurrentPosition:removeUpdate:${e.message}")
            }
            //超时直接返回默认的空对象
            Log.d(TAG, "getCurrentPosition:onError:${e.message}")
            return createDefaultLocation()
        }
    }

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    suspend fun repeatLocation(mLocationManager: LocationManager):Location{
        return  suspendCancellableCoroutine {continuation ->
            //获取最佳定位方式，如果获取不到则默认采用网络定位。
            var bestProvider = mLocationManager.getBestProvider(createCriteria(),true)
            if (bestProvider.isNullOrEmpty()||bestProvider == "passive"){
                bestProvider = "network"
            }
            Log.d(TAG, "getCurrentPosition:bestProvider:${bestProvider}")
            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    Log.d(TAG, "getCurrentPosition:onCompete:${location.latitude},${location.longitude}")
                    if (continuation.isActive){
                        continuation.resume(location)
                    }
                    mLocationManager.removeUpdates(this)
                }
                override fun onProviderDisabled(provider: String) {
                }

                override fun onProviderEnabled(provider: String) {
                }
            }
            //开始定位
            mLocationManager.requestLocationUpdates(bestProvider,1000, 0f, locationListener)
        }
    }



    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    fun getLastLocation( mLocationManager: LocationManager): Location {
        //获取最佳定位方式，如果获取不到则默认采用网络定位。
        var currentProvider = mLocationManager.getBestProvider(createCriteria(), true)
        if (currentProvider.isNullOrEmpty()||currentProvider == "passive"){
            currentProvider = "network"
        }
        return mLocationManager.getLastKnownLocation(currentProvider) ?: createDefaultLocation()
    }


    //创建定位默认值
    fun createDefaultLocation():Location{
        val location = Location("network")
        location.longitude = 0.0
        location.latitude = 0.0
        return location
    }

    private fun createCriteria():Criteria{
        return  Criteria().apply {
            accuracy = Criteria.ACCURACY_FINE
            isAltitudeRequired = false
            isBearingRequired = false
            isCostAllowed = true
            powerRequirement = Criteria.POWER_HIGH
            isSpeedRequired = false
        }
    }

    ///定位是否可用
    fun checkLocationManagerAvailable(mLocationManager: LocationManager):Boolean{
        return mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)||
                mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}