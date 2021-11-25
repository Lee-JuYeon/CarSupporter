package com.cavss.carsupporter.ui.view.views.adblue.mapview


import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.cavss.carsupporter.model.adblue.AdBlueModel
import com.cavss.carsupporter.vm.AdBlueVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AdBlueMapView(
    list : List<AdBlueModel>,
    adBlueVM : AdBlueVM
) {

    val context = LocalContext.current
    val mapView = remember {
        MapView(context)
    }
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    lifeCycle.addObserver(RemeberGoogleMapLifeCycle(mapView))

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp

        AndroidView(
            factory = {
                mapView.apply {
                    mapView.getMapAsync { googleMap ->
                        googleMap.uiSettings.setAllGesturesEnabled(true)

                        val location = adBlueVM.location.value
                        val position = LatLng(location.latitude, location.longitude)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,  15f))

                        googleMap.setOnCameraIdleListener {
                            val cameraPosition = googleMap.cameraPosition
                            adBlueVM.updateLocation(cameraPosition.target.latitude, cameraPosition.target.longitude)
                        }
                    }
                }
            },
            modifier = Modifier
                .width(screenWidth)
                .fillMaxHeight()
        )
    }
}

@Composable
fun RemeberGoogleMapLifeCycle(mapView : MapView) : LifecycleEventObserver = remember {
    LifecycleEventObserver { source, event ->
        when(event){
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    }
}

/*
 val mapView = RememberMapViewLifecycle()
    val currentLocation = adBlueVM.location.collectAsState()

 currentLocation.value.let {
            AndroidView(
                factory = { mapView }
            ) {

                mapView.getMapAsync { map ->

                    map.uiSettings.setAllGesturesEnabled(true)

                    val location = adBlueVM.location.value
                    val position = LatLng(location.latitude, location.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,  15f))

                    map.setOnCameraIdleListener {
                        val cameraPosition = map.cameraPosition
                        adBlueVM.updateLocation(cameraPosition.target.latitude, cameraPosition.target.longitude)
                    }
                }
            }
        }
 */