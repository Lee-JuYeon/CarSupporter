package com.cavss.carsupporter.ui.view.views.adblue.listview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cavss.carsupporter.R
import com.cavss.carsupporter.extensions.gradientBackground
import com.cavss.carsupporter.model.adblue.AdBlueModel

@Composable
fun AdBlueListView(list : List<AdBlueModel>) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .width(screenWidth)
            .background(Color.Black)
            .padding(5.dp),
        content = {
            items(list) { model ->
                AdBlueListItem(model = model)
            }
        }
    )
}


@Composable
fun AdBlueListItem(model : AdBlueModel){

    val gridentBackgroundColor : List<Color> = when(model.stock){
        in 1000.0 .. Double.MAX_VALUE -> {
            listOf(Color.Green.copy(0.2f), Color.Green.copy(0.0f),Color.Green.copy(0.0f), Color.Green.copy(0.0f),Color.Green.copy(0.9f))
        }
        in 300.0 .. 999.9 -> {
            listOf(Color.Yellow.copy(0.2f), Color.Yellow.copy(0.0f),Color.Yellow.copy(0.0f), Color.Yellow.copy(0.0f),Color.Yellow.copy(0.9f))
        }
        in 1.0 .. 299.9 -> {
            listOf(Color.Red.copy(0.2f), Color.Red.copy(0.0f),Color.Red.copy(0.0f), Color.Red.copy(0.0f),Color.Red.copy(0.9f))
        }
        else -> {
            listOf(Color.White.copy(0.2f), Color.White.copy(0.0f),Color.White.copy(0.0f), Color.White.copy(0.0f),Color.White.copy(0.9f))
        }
    }


    Column(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .gradientBackground(gridentBackgroundColor, 45f)
            .padding(5.dp)
    ) {
        // 지점 이름
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_spot),
                contentDescription = "지점 이름",
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .padding(5.dp),
                colorFilter = ColorFilter.tint(Color.Magenta)
            )
            Text(
                text = model.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(3.dp)
            )
        }
        Text(
            text = model.address,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    start = 33.dp,
                    end = 3.dp,
                    top = 3.dp,
                    bottom = 3.dp
                )
        )

        // 재고
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(top = 5.dp)
        ) {
            Text(
                text = stringResource(id = R.string.adblue_stock),
                color = Color.Green,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(2.dp)
                    .size(26.dp, 20.dp)
                    .border(1.dp, Color.Green, RoundedCornerShape(5.dp))
                    .padding(1.dp)
            )
            Text(
                text = "${model.stock}L",
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(3.dp)
            )
        }

        // 영업시간
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(top = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_time),
                contentDescription = "영업시간",
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .padding(5.dp),
                colorFilter = ColorFilter.tint(Color.LightGray)
            )
            Text(
                text = model.workTime,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(3.dp)
            )
        }

        // 전화번호
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_call),
                    contentDescription = "전화번호",
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .padding(5.dp),
                    colorFilter = ColorFilter.tint(Color.LightGray)
                )
                Text(
                    text = model.digit,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(3.dp)
                )
            }
            Text(
                text = "(${stringResource(id = R.string.adblue_updated)} : ${model.updateDate})",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(3.dp)
            )
        }
    }
}