package com.cavss.carsupporter.ui.view.custom.adview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.cavss.carsupporter.R
import com.cavss.carsupporter.backend.internet.InternetManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdView(type : AdMobType) {

    val internetManager = InternetManager(LocalContext.current)
//    val isNetworking = remember { mutableStateOf(false) }
    var isNetworking = false
    internetManager.observe(LocalContext.current as LifecycleOwner){ state ->
//        isNetworking.value = state
        isNetworking = state
    }

    when(type){
        AdMobType.BANNER -> {
            val BANNER_TEST_ID = "ca-app-pub-3940256099942544/6300978111"
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth(),
//                    .height(80.dp),
                factory = { context ->
                    AdView(context).apply {
                        adSize = AdSize.BANNER
                        adUnitId = R.string.admob_banner.toString()

                        loadAd(AdRequest.Builder().build())
                    }

                }
            )
        }
        AdMobType.FORWARD -> {

        }
        AdMobType.NATIVE -> {

        }
        AdMobType.OPEN -> {

        }
    }






}

enum class AdMobType(rawValue : String){
    BANNER(rawValue = "BANNER"),
    FORWARD(rawValue = "FORWARD"),
    NATIVE(rawValue = "NATIVE"),
    OPEN(rawValue = "OPEN")
}

/*
 val isInEditMode = LocalInspectionMode.current
    if (isInEditMode) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(horizontal = 2.dp, vertical = 6.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            text = "Advert Here",
        )
    } else {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    adSize = AdSize.BANNER
                    adUnitId = BANNER_TEST_ID
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
 */