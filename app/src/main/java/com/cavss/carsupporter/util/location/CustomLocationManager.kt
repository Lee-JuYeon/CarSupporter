package com.cavss.carsupporter.util.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.cavss.carsupporter.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class CustomLocationManager {
    private var context : Context? = null
    fun setContext(context : Context) { this.context = context}

    private fun locationClient() : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context ?: MainActivity().applicationContext)

    lateinit var locationCallBack : LocationCallback
    private fun setLocationCallback(){
        try {
            locationCallBack = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    if (locationResult == null) return
                    for (location in locationResult.locations) {
                        if (location != null) {
                            val latitude = location.latitude
                            val longitude = location.longitude
                            Log.e("mException", "GPS Location changed, Latitude: $latitude" +
                                    ", Longitude: $longitude")
                        }
                    }
                }
            }
        }catch (e:Exception){
            Log.e("mException", "CustomLocationManager, setLocationCallback // Exception : ${e.message}")
        }
    }

    fun initialisation() {
        locationClient()
        setLocationCallback()
    }

    private val fireOfficeLocation = LatLng(36.484162478124276, 127.261165725576)
    fun getCurrentlocation() : LatLng {
        return when {
            context != null -> {
                if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                        var latLng : LatLng? = null
                        locationClient().lastLocation.addOnSuccessListener { location : Location? ->
                            val currentLatitude = location?.latitude ?: fireOfficeLocation.latitude
                            val currentLongitude = location?.longitude ?: fireOfficeLocation.longitude

                            latLng =  LatLng(currentLatitude, currentLongitude)
                        }
                        latLng ?: fireOfficeLocation
                    /*
                    null이 리턴되는 경우 :
                    1. 위치 정보를 얻은 적이 없을 때
                    2. 디바이스의 Location 사용이 중지되는 경우 캐시 정보 삭제되기 때문에 null return
                    3. google play 서비스가 재실행되었을 때, 저장된 위치 정보가 없기 때문에 null return
                     */
                }else{
                    Log.e("mException", "CustomLocationManager, getCurrentLocation // Exception : 위치 권한이 승낙되지 않았음.")
                    fireOfficeLocation
                }
            }
            else -> {
                Log.e("mException", "CustomLocationManager, getCurrentLocation // Exception : Context가 null")
                fireOfficeLocation
            }
        }
//        if (context != null){
//            if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//                locationClient().lastLocation.addOnSuccessListener { location : Location? ->
//                    val currentLatitude = location?.latitude ?: fireOfficeLocation.latitude
//                    val currentLongitude = location?.longitude ?: fireOfficeLocation.longitude
//
//                    LatLng(currentLatitude, currentLongitude)
//                }
//                /*
//                null이 리턴되는 경우 :
//                1. 위치 정보를 얻은 적이 없을 때
//                2. 디바이스의 Location 사용이 중지되는 경우 캐시 정보 삭제되기 때문에 null return
//                3. google play 서비스가 재실행되었을 때, 저장된 위치 정보가 없기 때문에 null return
//                 */
//            }else{
//                Log.e("mException", "CustomLocationManager, getCurrentLocation // Exception : 위치 권한이 승낙되지 않았음.")
//            }
//        }else{
//            Log.e("mException", "CustomLocationManager, getCurrentLocation // Exception : Context가 null")
//        }
    }

    fun de_initalisation(){
        locationClient().removeLocationUpdates(locationCallBack)
    }
}