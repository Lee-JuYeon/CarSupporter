package com.cavss.carsupporter.ui.view.views.adblue.mapview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.cavss.carsupporter.R
import com.google.android.gms.maps.MapView

@Composable
fun RemeberMapViewLifecycle() : MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply { id = R.id.background }
    }
    val lifeCycleObserver = RememberMapLifeCycleObserver(mapView = mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle){
        lifecycle.addObserver(lifeCycleObserver)
        onDispose { lifecycle.removeObserver(lifeCycleObserver) }
    }
    return  mapView
}