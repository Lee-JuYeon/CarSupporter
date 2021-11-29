package com.cavss.carsupporter.ui.view.views.adblue.listview

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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


@Composable
fun AdBlueListItem(
    model : AdBlueModel,
    whenClicked : (Map<String, Double>) -> Unit
){

    val customBorderColour : Color = when(model.stock){
        in 1000.0 .. Double.MAX_VALUE -> Color.Green
        in 300.0 .. 999.9 -> Color.Yellow
        else -> Color.Red
    }

    Column(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                customBorderColour,
                RoundedCornerShape(10.dp)
            )
            .padding(5.dp)
            .clickable {
                whenClicked(
                    mapOf(
                        "lati" to model.latitude,
                        "long" to model.longitude
                    )
                )
            }
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
            fontWeight = FontWeight.SemiBold,
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

        // 가격
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_money),
                contentDescription = "가격",
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .padding(5.dp),
            )
            Text(
                text = "${model.price}₩",
                color = Color.White,
                fontSize = 18.sp,
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
    }
}