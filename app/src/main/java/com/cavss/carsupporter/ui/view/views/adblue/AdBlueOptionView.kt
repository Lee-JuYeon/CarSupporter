package com.cavss.carsupporter.ui.view.views.adblue

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cavss.carsupporter.R
import com.cavss.carsupporter.model.adblue.AdBlueModel
import com.cavss.carsupporter.vm.AdBlueVM

@Composable
fun AdBlueOptionView(
    adBlueVM : AdBlueVM,
    adBlueShopList : MutableState<List<AdBlueModel>>
) {
    LazyRow(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            val optionList = listOf<Int>(
                R.string.tab_enough,
                R.string.tab_cheap
            )
            items(optionList){ option ->
                Text(
                    text = stringResource(id = option),
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            1.dp,
                            Color.White,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                        .clickable {
                            when(option){
                                R.string.tab_cheap -> {
                                    adBlueVM.setAdBlueList(
                                        adBlueShopList.value
                                            .sortedBy { it.price }
                                    )
                                }
                                R.string.tab_enough -> {
                                    adBlueVM.setAdBlueList(
                                        adBlueShopList.value
                                            .sortedByDescending { it.stock }
                                    )
                                }
                            }
                        }
                )
            }
        }
    )

}