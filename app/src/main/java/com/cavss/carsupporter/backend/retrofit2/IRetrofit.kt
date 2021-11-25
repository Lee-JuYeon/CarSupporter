package com.cavss.carsupporter.backend.retrofit2

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {
    // https://www.unsplash.com/ search/photos  /?query=""
    @GET(API.BASEURL_DEF)
    fun GET_DefShopList(
        @Query("page") page : Int,
        @Query("perPage") perPage : Int
    ) : Call<JsonElement>
}