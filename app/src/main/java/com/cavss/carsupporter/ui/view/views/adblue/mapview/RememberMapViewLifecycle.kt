package com.cavss.carsupporter.ui.view.views.adblue.mapview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.google.android.gms.maps.MapView

@Composable
fun RememberMapViewLifecycle() : MapView {
    val context = LocalContext.current

    val mapView = remember {
        MapView(context).apply {
        }
    }

    val lifeCycleObserver = RememberMapLifeCycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle){
        lifecycle.addObserver(lifeCycleObserver)
        onDispose {
            lifecycle.removeObserver(lifeCycleObserver)
        }
    }

    return  mapView
}