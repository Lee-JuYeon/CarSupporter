package com.cavss.carsupporter.ui.view.views.adblue.mapview

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MapPinOverlay(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ){
//            Image(
//                modifier = Modifier.size(50.dp),
//                bitmap = ImageBitmap.imageResource(id = R.drawable.pin).asAndroidBitmap().asImageBitmap(),
//                contentDescription = "Pin Image"
//            )
        }
        Box(
            Modifier.weight(1f)
        ){

        }
    }
}