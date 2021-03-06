package com.cavss.carsupporter.ui.view.views.adblue

import android.Manifest
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.cavss.carsupporter.R
import com.cavss.carsupporter.model.adblue.AdBlueModel
import com.cavss.carsupporter.ui.view.views.adblue.listview.AdBlueListView
import com.cavss.carsupporter.ui.view.views.adblue.mapview.AdBlueMapView
import com.cavss.carsupporter.ui.view.views.adblue.mapview.RememberMapLifeCycleObserver
import com.cavss.carsupporter.util.permission.MultiRequestPermission
import com.cavss.carsupporter.vm.AdBlueVM
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AdBlueView(adBlueVM: AdBlueVM) {

    val adBlueShopList = remember { mutableStateOf(listOf<AdBlueModel>()) }
    adBlueVM.getAdBlueList.observe(LocalLifecycleOwner.current) { list ->
        adBlueShopList.value = list
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

//        // TextFiled View
//        TextField(
//            value = adBlueVM.getText.value,
//            onValueChange = {
//                adBlueVM.setText(it)
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(80.dp)
//                .padding(5.dp)
//        )

        AdBlueColorSetView()

        MultiRequestPermission(
            permissionList = listOf(
                Manifest.permission.INTERNET,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            modifier = Modifier
                .fillMaxSize(),
            whenGrated = {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // option List
                    AdBlueOptionView(adBlueVM, adBlueShopList)

                    // ListView
                    AdBlueListView(
                        list = adBlueShopList.value,
                        adBlueVM = adBlueVM
                    )

                    // MapView
                    AdBlueMapView(
                        list = adBlueShopList,
                        adBlueVM = adBlueVM
                    )
                }
            },
            whenDenied = {
                Text(
                    text = stringResource(id = R.string.permission_can_not_use_by_denied),
                    color = Color.White
                )
            }
        )
    }
}
/*
 val horizontalScrollState = rememberScrollState(0)
 val scope = rememberCoroutineScope()
  Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(
                    state = horizontalScrollState,
                    enabled = false
                )
        ) {
            // MapView
            AdBlueMapView(
                list = adBlueShopList,
                adBlueVM = adBlueVM
            )

            // ListView
            AdBlueListView(
                list = adBlueShopList.value
            )
        }

 */
/*
 // Chips View
        LazyRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier,
            content = {
                val tabList = listOf<Int>( R.string.tab_enough, R.string.tab_map, R.string.tab_list)
                val tabColour = Color.LightGray
                items(tabList){ title ->
                    Text(
                        text = stringResource(id = title),
                        color = tabColour,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .border(1.dp, tabColour, RoundedCornerShape(5.dp))
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = 5.dp,
                                bottom = 5.dp
                            )
                            .clickable {
                                when (title) {
                                    R.string.tab_nearby -> {

                                    }
                                    R.string.tab_enough -> {
                                        adBlueShopList.value.sortedByDescending { it.stock }
                                    }
                                    R.string.tab_cheap -> {

                                    }
                                }
                            }
                    )
                }
            }
        )
 */