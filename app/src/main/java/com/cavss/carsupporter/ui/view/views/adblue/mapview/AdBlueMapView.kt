package com.cavss.carsupporter.ui.view.views.adblue.mapview


import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.cavss.carsupporter.R
import com.cavss.carsupporter.model.adblue.AdBlueModel
import com.cavss.carsupporter.vm.AdBlueVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


@Composable
fun AdBlueMapView(
    list : MutableState<List<AdBlueModel>>,
    adBlueVM : AdBlueVM
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val context = LocalContext.current
    val mapView = remember {
        MapView(context)
    }
    val lifeCycleOwner = LocalLifecycleOwner.current
    val lifeCycle = lifeCycleOwner.lifecycle
    lifeCycle.addObserver(RememberMapLifeCycleObserver(mapView))

    val clickedPosition = remember { mutableStateOf(mapOf("lati" to 0.0, "long" to 0.0)) }
    adBlueVM.getLocation.observe(lifeCycleOwner) { location ->
        try{
            clickedPosition.value = location
        }catch (e:Exception){
            Log.e("mException", "AdblueMapView, adBlueVM.getlocation.observe // Exception ; ${e.message}")
        }catch (e:NumberFormatException){
            Log.e("mException", "AdblueMapView, adBlueVM.getlocation.observe // NumberFormatException ; ${e.message}")
        }
    }

    AndroidView(
        factory = {
            mapView.apply {
                mapView.getMapAsync { googleMap ->
                    googleMap.apply {
                        uiSettings.setAllGesturesEnabled(true)
                        uiSettings.isMyLocationButtonEnabled = true

                        list.value.forEach { adBlueModel ->
                            googleMap.let { map ->
                                try {
                                    map.addMarker(
                                        MarkerOptions()
                                            .icon(
                                                when(adBlueModel.stock){
                                                    in 1000.0 .. Double.MAX_VALUE -> {
                                                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                                                    }
                                                    in 300.0 .. 999.9 -> {
                                                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                                                    }
                                                    else -> {
                                                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                                                    }
                                                }
                                            )
                                            .position(LatLng(adBlueModel.latitude, adBlueModel.longitude))
                                    )
                                    val customInfoWindowAdapter = CustomInfoWindowAdapter(it)
                                    customInfoWindowAdapter.let { adapter ->
                                        adapter.setAdBlueModel(adBlueModel)
                                        map.setInfoWindowAdapter(adapter)
                                    }
                                }catch (e:Exception){
                                    Log.e("mException", "AdBlueMapView, AndroidView, googleMap, addMarker // Exception : ${e.message}")
                                }catch (e:NumberFormatException){
                                    Log.e("mException", "AdBlueMapView, AndroidView, googleMap, addMarker // NumberFormatException : ${e.message}")
                                }
                            }
                        }

                        adBlueVM.getLocation.observe(lifeCycleOwner) { location ->
                            try {
                                moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(
                                            location["lati"] ?: 0.0,
                                            location["long"] ?: 0.0
                                        ),
                                        15f
                                    )
                                )
                            }catch (e:Exception){
                                Log.e("mException", "AdBlueMapView, AndroidView, googleMap, getLocation.observe // Exception : ${e.message}")
                            }catch (e:NumberFormatException){
                                Log.e("mException", "AdBlueMapView, AndroidView, googleMap, getLocation.observe // NumberFormatException : ${e.message}")
                            }
                        }
                    }
                }
            }
        },
        modifier = Modifier
            .width(screenWidth)
            .fillMaxHeight()
    )
}

class CustomInfoWindowAdapter(context : Context) : GoogleMap.InfoWindowAdapter {
    var mContext = context

    private var adBlueModel : AdBlueModel? = null
    fun setAdBlueModel(model : AdBlueModel){ this.adBlueModel = model }

    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.layout_map, null)
    private fun rendowWindowText(marker: Marker, view: View){

        val pinTitle = view.findViewById<TextView>(R.id.pin_title)
        val pinAddress = view.findViewById<TextView>(R.id.pin_address)
        val pinStock = view.findViewById<TextView>(R.id.pin_stock)
        val pinPrice = view.findViewById<TextView>(R.id.pin_price)
        val pinWorkingTime = view.findViewById<TextView>(R.id.pin_workingtime)
        val pinDigit = view.findViewById<TextView>(R.id.pin_digit)

        pinTitle.text = adBlueModel?.title
        pinAddress.text = "주소 : ${adBlueModel?.address}"
        pinStock.text = "재고 : ${adBlueModel?.stock}ℓ"
        pinPrice.text = "가격 : ${adBlueModel?.price}₩"
        pinWorkingTime.text = "영업시간 : ${adBlueModel?.workTime}"
        pinDigit.text = "전화번호 : ${adBlueModel?.digit}"
    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}
/*
setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                                    override fun getInfoWindow(marker: Marker): View? = null
                                    override fun getInfoContents(marker: Marker): View {
                                        val infoLayout = LinearLayout(context)
                                        infoLayout.let{
                                            it.orientation = LinearLayout.VERTICAL

                                            val snippet : TextView = TextView(context)
                                            snippet.gravity = Gravity.LEFT
                                            snippet.text = "${model.title}\n${model.address}"
                                            snippet.setTypeface(null, Typeface.BOLD);

                                            it.addView(snippet)
                                        }
                                        return infoLayout
                                    }
                                })
                                setOnInfoWindowClickListener { marker ->
                                    Log.e("mException", "title : ${model.title}")
                                }

            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                val locationResult =  fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener { task ->
                    when{
                        task.isSuccessful -> {
                            val currentLocation = task.result
                            if (currentLocation != null){
                                adBlueVM.getCurrentLocation(
                                    currentLocation.latitude,
                                    currentLocation.longitude
                                )
                            }
                        }
                        task.isComplete -> {

                        }
                        task.isCanceled -> {

                        }
                    }
                }
            }else{

            }

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