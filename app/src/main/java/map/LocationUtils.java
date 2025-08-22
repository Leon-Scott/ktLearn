package map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationUtils {

    private static FusedLocationProviderClient fusedLocationClient;
    private static LocationCallback locationCallback;
    private static LocationRequest locationRequest;
    private static OnLocationListener onLocationListener;

    // 定义获取位置信息成功的监听器接口
    public interface OnLocationListener {
        void onLocationChanged(Location location);
    }

    // 初始化相关参数
    public static void init(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000); // 设置位置更新间隔时间（单位：毫秒）
        locationRequest.setFastestInterval(2000); // 设置最快位置更新间隔时间（单位：毫秒）

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d("xxx3","startLocationUpdates result ="+(locationResult!= null));
                if (locationResult!= null) {
                    Location location = locationResult.getLastLocation();
                    Log.d("xxx3","startLocationUpdates result ="+(location!= null));
                    if (location!= null && onLocationListener!= null) {
                        onLocationListener.onLocationChanged(location);
                    }
                }
            }
        };
    }

    // 开始获取位置信息
    public static void startLocationUpdates(Context context, OnLocationListener listener) {
        Log.d("xxx3","startLocationUpdates ");
        onLocationListener = listener;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            Log.d("xxx3","startLocationUpdates permission deny");
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    // 停止获取位置信息
    public static void stopLocationUpdates() {
        if (fusedLocationClient!= null && locationCallback!= null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    // 获取一次位置信息（异步方式）
    public static void getCurrentLocationOnce(Context context, final OnLocationListener listener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            Log.d("xxx3","getCurrentLocationOnce no permission");
            return;
        }
        Task<Location> locationTask = fusedLocationClient.getLastLocation();
        locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Log.d("xxx3","getCurrentLocationOnce return result="+task.isSuccessful());
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    Log.d("xxx3","getCurrentLocationOnce return result=="+(location!= null)+","+(listener!= null));
                    if (location!= null && listener!= null) {
                        Log.d("xxx3", "getCurrentLocationOnce return result=" + location.getLatitude() + "," + location.getLongitude());
                        listener.onLocationChanged(location);
                    }
                } else {
                    Log.e("xxx3","getCurrentLocationOnce return result failed="+task.getException().getMessage());

                }
            }
        });
    }
}
