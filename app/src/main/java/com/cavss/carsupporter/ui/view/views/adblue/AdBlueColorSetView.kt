package com.cavss.carsupporter.ui.view.views.adblue

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cavss.carsupporter.R

@Composable
fun AdBlueColorSetView(){
    // Color sample
    LazyRow(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier,
        content = {
            val rangeList = listOf<Int>(
                R.string.adblue_range_1000,
                R.string.adblue_range_300,
                R.string.adblue_range_1,
                R.string.adblue_range_0
            )
            items(rangeList){ range ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(
                            1.dp,
                            Color.DarkGray,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(10.dp, 10.dp)
                            .background(
                                when (range) {
                                    R.string.adblue_range_1000 -> {
                                        Color.Green
                                    }
                                    R.string.adblue_range_300 -> {
                                        Color.Yellow
                                    }
                                    R.string.adblue_range_1 -> {
                                        Color.Red
                                    }
                                    R.string.adblue_range_0 -> {
                                        Color.White
                                    }
                                    else -> Color.White
                                }
                            )
                    )
                    Text(
                        text = stringResource(id = range),
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                }
            }
        }
    )
}