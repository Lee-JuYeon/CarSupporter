package com.cavss.carsupporter.ui.view.custom.tabview

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun <HEADER, BODY> CustomTabView(
    headerItems : List<HEADER>,
    bodyItems : List<BODY>,
    headerItem : @Composable (HEADER) -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val currentIndex = remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(
                    width = screenWidth,
                    height = 80.dp
                )
        ) {
            headerItems.forEachIndexed { index, model ->
                headerItem(model)
            }
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .size(
                    width = screenWidth,
                    height = screenHeight - 160.dp
                )
                .offset(x = if (currentIndex.value == 0) 0.dp else screenWidth)
        ) {
//            DefListView(list = defVM.getDefList.value ?: listOf())
//            DefMapView(list = defVM.getDefList.value ?: listOf())
        }
    }
}