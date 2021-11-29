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
            perPage = 500
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
                        price = 0,
                        latitude = 0.0,
                        longitude = 0.0,
                        updateDate = ""
                    )
                )
                completion(dataArray)
                Log.e("mException", "RetrofitManager, getDefShopList, onFailure // Throwable : ${t.message}")
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when(response.code()){
                    200 -> {
                        try {
                            response.body()?.let {
                                val body = it.asJsonObject
                                val data = body.getAsJsonArray("data")

                                data.forEach { resultItem ->
                                    try {
                                        val resultItemObject = resultItem.asJsonObject

                                        val price = resultItemObject.get("price").asString // 가격
                                        val longitude = resultItemObject.get("lng").asString // 경도
                                        val updateDate =
                                            resultItemObject.get("regDt").asString // 데이터기준일
                                        val title = resultItemObject.get("name").asString // 명칭
                                        val workTime =
                                            resultItemObject.get("openTime").asString // 영업시간
                                        val latitude = resultItemObject.get("lat").asString // 위도
                                        val stock =
                                            resultItemObject.get("inventory").asString // 재고량
                                        val digit = resultItemObject.get("tel").asString // 전화번호
                                        val address = resultItemObject.get("addr").asString // 주소
                                        val code = resultItemObject.get("code").asString // 코드

                                        val photoItem = AdBlueModel(
                                            price = price.toInt(),
                                            longitude = longitude.toDouble(),
                                            updateDate = updateDate,
                                            title = title,
                                            workTime = workTime,
                                            latitude = latitude.toDouble(),
                                            stock = stock.toDouble(),
                                            digit = digit,
                                            address = address,
                                            code = code
                                        )
                                        dataArray.add(photoItem)
                                    } catch (e: Exception) {
                                        Log.e(
                                            "mException",
                                            "RetrofitManager, getDefShopList, onResponse, 200, list 작업 // Exception : ${e.message}"
                                        )
                                    } catch (e: NumberFormatException) {
                                        Log.e(
                                            "mException",
                                            "RetrofitManager, getDefShopList, onResponse, 200, list 작업 // NumberFormatException : ${e.message}"
                                        )
                                    }
                                }
                                completion(dataArray)
                            }
                        }catch (e:UnsupportedOperationException){
                            Log.e("mException", "RetrofitManager, getDefShopList, onResponse, 200 // UnsupportedOperationException : ${e.localizedMessage}" )
                        }catch (e:Exception){
                            Log.e("mException", "RetrofitManager, getDefShopList, onResponse, 200 // Exception : ${e.localizedMessage}" )
                        }catch (e:NumberFormatException){
                            Log.e("mException", "RetrofitManager, getDefShopList, onResponse, 200 // NumberFormatException : ${e.message}" )
                        }
                    }
                    else -> {
                        Log.e("mException", "other error code : ${response.code()}")
                    }
                }
            }
        })
    }
}