package com.example.mayweather;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnSuccessListener;

public class Locations extends Activity {
    public static double lat;
    public static double lon;
    MainActivity mainActivity = new MainActivity();

    public void setLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {  // 위치접근 권한 유무 확인
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    mainActivity.REQUEST_CODE_PERMISSIONS); // 사용자에게 위치접근 요청
            return;
        }
        mainActivity.mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());  //위도, 경도 잡음
                    lat = location.getLatitude();
                    lon = location.getLongitude();  // 위도, 경도를 변수에 저장
                }
            }
        });
    }


    private double get_lat() {
        return (lat);
    }
    private double get_lon() {
        return (lon);
    }
}
