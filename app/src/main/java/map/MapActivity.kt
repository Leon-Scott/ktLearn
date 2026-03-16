package map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.myapplication.R
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale


class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // 检查并获取位置信息
        getDeviceLocation();
    }

    lateinit var geocoder:Geocoder
    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        geocoder = Geocoder(this, Locale.getDefault())
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            getAddress(latitude, longitude)
        } else {
            val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            }
            CoroutineScope(exceptionHandler).launch {
                Log.d("xxx3","LocationManagerUtils")
                var loc = LocationManagerUtils.getCurrentPosition(locationManager)
                val latitude = loc.latitude
                val longitude = loc.longitude
                getAddress(latitude, longitude)
            }
        }
//        getAddress(37.7749,-122.4194)
//        getAddress(22.62,114.07)
//        getAddress(35.69,139.69)

        // 初始化位置工具类
        LocationUtils.init(this);

        // 获取一次位置信息
        LocationUtils.getCurrentLocationOnce(this, LocationUtils.OnLocationListener {
            if (it != null) {
                val latitude = it.latitude
                val longitude = it.longitude
                Log.d("xxx3","纬度: $latitude, 经度: $longitude")
            } else {
                Toast.makeText(this, "获取位置信息失败", Toast.LENGTH_SHORT).show()
            }
        });

//        LocationUtils.startLocationUpdates(this, LocationUtils.OnLocationListener {
//            if (location != null) {
//                val latitude = location.latitude
//                val longitude = location.longitude
//                Log.d("xxx3","4纬度: $latitude, 4经度: $longitude")
//            } else {
//                Toast.makeText(this, "4获取位置信息失败", Toast.LENGTH_SHORT).show()
//            }
//        });
    }

    fun getAddress(latitude:Double,longitude:Double){
        Log.d("xxx3","lati=$latitude,longi=$longitude")
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.size > 0) {
                Log.d("xxx3","地址=${Gson().toJson(addresses)}")
                val address: Address = addresses[0]
                val addressLine: String = address.getAddressLine(0)
                val city: String = address.getLocality()
                val country: String = address.getCountryName()
                // 可以根据需要获取更多的地址信息，如邮编（address.getPostalCode()）等
//                println("地址: $addressLine, 城市: $city, 国家: $country")
                Log.d("xxx3","城市: $city, 国家: $country")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private lateinit var locationManager: LocationManager
    private fun getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("xxx3","getDeviceLocation no permission")
            // 如果没有权限，这里可以进行权限申请相关操作，比如动态申请权限（以下为简单示意）
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            ) // 100为请求码，可自定义
            return
        }

       /* // 尝试通过GPS获取位置信息
        val gpsLocation: Location? =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        // 尝试通过网络定位获取位置信息
        val networkLocation: Location? =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        var bestLocation: Location? = null

        // 比较GPS和网络定位获取到的位置信息，选择较优的一个（通常GPS更精准但可能耗时久、网络定位较快但精度稍低）
        if (gpsLocation != null && networkLocation != null) {
            bestLocation =
                if (gpsLocation.accuracy < networkLocation.accuracy) gpsLocation else networkLocation
        } else if (gpsLocation != null) {
            bestLocation = gpsLocation
        } else if (networkLocation != null) {
            bestLocation = networkLocation
        }
        if (bestLocation != null) {
            val latitude = bestLocation.latitude
            val longitude = bestLocation.longitude
            // 这里可以根据需求对获取到的经纬度进行后续处理，比如展示在界面上等等
            println("xxx3 getDeviceLocation 纬度: $latitude, 经度: $longitude")
        } else {
            // 如果没有获取到有效的位置信息，可进行相应提示或其他处理
            println("xxx3 getDeviceLocation 无法获取设备位置信息")
        }*/


        var criteria = Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度，如果设置为高精度，依然获取不了location。
//        criteria.setAltitudeRequired(false);//不要求海拔
//        criteria.setBearingRequired(false);//不要求方位
//        criteria.setCostAllowed(true);//允许有花费
//        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

        var locationProvider = locationManager.getBestProvider(criteria, true);
        Log.d("xxx3", "onCreate1: " + locationProvider.toString());
        var location = locationManager.getLastKnownLocation(locationProvider!!);
        Log.d("xxx3", "onCreate2: " + (location == null) + ".."+locationManager.isLocationEnabled);
        Log.d("xxx3", "onCreate3: " + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        Log.d("xxx3", "onCreate4: " + locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
        if (location != null) {
            Log.d("xxx3", "onCreate: location");
            //不为空,显示地理位置经纬度
            Log.d("xxx3","criteria getLocation 纬度: ${location.latitude}, 经度: ${location.longitude}")
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider!!, 0L, 0f, object :LocationListener {
            override fun onLocationChanged(location: Location) {
                TODO("Not yet implemented")
                Log.d("xxx3","criteria getLocation changed 纬度: ${location.latitude}, 经度: ${location.longitude}")
            }
        })


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予，再次尝试获取位置信息
                getDeviceLocation()
            } else {
                // 权限被拒绝，进行相应提示等处理
                println("xxx3 用户拒绝了位置权限申请")
            }
        }
    }

}