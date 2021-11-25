package com.cavss.carsupporter.repository

import android.util.Log
import com.cavss.carsupporter.backend.ktor.KtorClient
import com.cavss.carsupporter.backend.retrofit2.RetrofitManager
import com.cavss.carsupporter.model.adblue.AdBlueModel
import io.ktor.client.request.*


object AdBlueRepository {
    suspend fun fetchShop() : List<AdBlueModel> {
        val fullPath = "https://api.odcloud.kr/api/15094782/v1/uddi:6b2017af-659d-437e-a549-c59788817675?page=1&perPage=200&serviceKey=%2FJvgxqsdfPdWOZcw7KkvmxoYRaK%2FEc5UnnG%2FBxar0C6Pbjyw33bkPjobwqsGfcRFt7I%2BqYsBfmpU%2B1J0eEA2Ng%3D%3D"
        val endPoint = "https://infuser.odcloud.kr/oas/docs?namespace=15094782/v1"
        return KtorClient.HTTP_CLIENT.use {
            it.get(fullPath)
        }
    }

    suspend fun fetchAdBlueShop_Retrofit() : List<AdBlueModel> {
        var emptyList = listOf<AdBlueModel>()
        RetrofitManager.instance.getDefShopList {
            Log.e("mException", "AdBlueRepositoy 오리진 = ${it}")
            emptyList =  it
        }
        Log.e("mException", "AdBlueRepositoy emptyList = ${emptyList}")
        return emptyList
    }
}