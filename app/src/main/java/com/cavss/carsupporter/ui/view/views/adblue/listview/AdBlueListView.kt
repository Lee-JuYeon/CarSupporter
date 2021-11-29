package com.cavss.carsupporter.ui.view.views.adblue.listview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cavss.carsupporter.R
import com.cavss.carsupporter.model.adblue.AdBlueModel
import com.cavss.carsupporter.vm.AdBlueVM

@Composable
fun AdBlueListView(
    list : List<AdBlueModel>,
    adBlueVM: AdBlueVM
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.Black)
            .padding(5.dp)
            .fillMaxWidth()
            .height(height = 200.dp),
        content = {
            items(list) { model ->
                AdBlueListItem(
                    model = model,
                    whenClicked = { map ->
                        adBlueVM.setLocation(map)
                    }
                )
            }
        }
    )
}