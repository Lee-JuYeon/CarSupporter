package com.cavss.carsupporter

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.cavss.carsupporter.ui.theme.CarSupporterTheme
import com.cavss.carsupporter.ui.view.custom.adview.AdMobType
import com.cavss.carsupporter.ui.view.custom.adview.AdView
import com.cavss.carsupporter.ui.view.views.adblue.AdBlueView
import com.cavss.carsupporter.util.permission.MultiRequestPermission
import com.cavss.carsupporter.vm.AdBlueVM

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
}
