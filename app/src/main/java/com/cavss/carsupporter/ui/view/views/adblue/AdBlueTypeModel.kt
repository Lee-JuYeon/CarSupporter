package com.cavss.carsupporter.ui.view.views.adblue

sealed class AdBlueTypeModel(
    val id : AdBlueViewType,
    val title : String,
    val image : Int
) {
    object AdBlueList : AdBlueTypeModel(id = AdBlueViewType.LIST, title = "LIST", image = 0)
    object AdBlueMap : AdBlueTypeModel(id = AdBlueViewType.MAP, title = "MAP", image = 1)

    object AdBlue_LIST {
        val list = listOf(
            AdBlueList, AdBlueMap
        )
    }
}