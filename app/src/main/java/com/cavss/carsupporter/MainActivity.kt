package com.cavss.carsupporter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.cavss.carsupporter.backend.retrofit2.RetrofitManager
import com.cavss.carsupporter.ui.theme.CarSupporterTheme
import com.cavss.carsupporter.ui.view.custom.adview.AdMobType
import com.cavss.carsupporter.ui.view.custom.adview.AdView
import com.cavss.carsupporter.ui.view.views.adblue.AdBlueView
import com.cavss.carsupporter.util.location.CustomLocationManager
import com.cavss.carsupporter.vm.AdBlueVM
import com.google.android.gms.maps.model.LatLng

class MainActivity : ComponentActivity() {

    private val adBlueVM by viewModels<AdBlueVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarSupporterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        AdView(type = AdMobType.BANNER)
                        AdBlueView(adBlueVM = adBlueVM)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setCustomLocationManager()
    }

    private var customLocationManager : CustomLocationManager? = null
    private fun setCustomLocationManager(){
        try{
            if (customLocationManager == null) customLocationManager = CustomLocationManager()
            customLocationManager?.apply {
                setContext(this@MainActivity)
                initialisation()

                val currentLocation : LatLng = getCurrentlocation()
                adBlueVM.setLocation(
                    mapOf(
                        "lati" to currentLocation.latitude,
                        "long" to currentLocation.longitude
                    )
                )
            }
        }catch (e:Exception){
            Log.e("mException", "MainActivity, setCustomLocationManager // Exception : ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        customLocationManager?.de_initalisation()
        customLocationManager = null
    }
}
