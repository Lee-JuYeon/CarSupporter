package com.cavss.carsupporter.ui.view.views.adblue.mapview

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.cavss.carsupporter.vm.AdBlueVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng


@Composable
private fun MapViewContainer(
    isEnabled: Boolean,
    mapView: MapView,
    viewModel: AdBlueVM
) {
    AndroidView(
        factory = { mapView }
    ) {

        mapView.getMapAsync { map ->

            map.uiSettings.setAllGesturesEnabled(isEnabled)

            val location = viewModel.location.value
            val position = LatLng(location.latitude, location.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,  15f))

            map.setOnCameraIdleListener {
                val cameraPosition = map.cameraPosition
                viewModel.updateLocation(cameraPosition.target.latitude, cameraPosition.target.longitude)
            }
        }

    }
}