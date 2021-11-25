package com.cavss.carsupporter.backend.retrofit2

import android.util.Log
import com.cavss.carsupporter.model.adblue.AdBlueModel
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    // http 콜 만들기
    private val iRetrofit : IRetrofit? = RetrofitClient.DEF_Client(API.BASEURL_DEF)?.create(IRetrofit::class.java)

    // 요소수 매장 api호출
    fun getDefShopList(completion : (ArrayList<AdBlueModel>) -> Unit){
        val call = iRetrofit?.GET_DefShopList(
            page = 1,
            perPage = 200
        ) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            var dataArray = ArrayList<AdBlueModel>()
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                dataArray.add(
                    AdBlueModel(
                        code = "",
                        title = "",
                        address = "",
                        digit = "",
                        workTime = "",
                        stock = 0.0,
                        price = "",
                        latitude = "",
                        longitude = "",
                        updateDate = ""
                    )
                )
                completion(dataArray)
                Log.e("mException", "RetrofitManager, getDefShopList, onFailure // Throwable : ${t.message}")
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when(response.code()){
                    200 -> {
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.getAsJsonArray("data")

                            val currentCount = body.get("currentCount").asInt
                            if (currentCount == 0){
                                dataArray.add(
                                    AdBlueModel(
                                        code = "",
                                        title = "",
                                        address = "",
                                        digit = "",
                                        workTime = "",
                                        stock = 0.0,
                                        price = "",
                                        latitude = "",
                                        longitude = "",
                                        updateDate = ""
                                    )
                                )
                            }else{
                                data.forEach { resultItem ->
                                    val resultItemObject = resultItem.asJsonObject

                                    val price = resultItemObject.get("가격").asString // 가격
                                    val longitude = resultItemObject.get("경도").asString // 경도
                                    val updateDate = resultItemObject.get("데이터기준일").asString // 데이터기준일
                                    val title = resultItemObject.get("명칭").asString // 명칭
                                    val workTime = resultItemObject.get("영업시간").asString // 영업시간
                                    val latitude = resultItemObject.get("위도").asString // 위도
                                    val stock = resultItemObject.get("재고량").asDouble // 재고량
                                    val digit = resultItemObject.get("전화번호").asString // 전화번호
                                    val address = resultItemObject.get("주소").asString // 주소
                                    val code = resultItemObject.get("코드").asString // 코드

                                    val photoItem = AdBlueModel(
                                        price = price,
                                        longitude = longitude,
                                        updateDate = updateDate,
                                        title = title,
                                        workTime = workTime,
                                        latitude = latitude,
                                        stock = stock,
                                        digit = digit,
                                        address = address,
                                        code = code
                                    )
                                    dataArray.add(photoItem)
                                }
                            }
                            completion(dataArray)
                        }
                    }
                }
            }
        })
    }
}