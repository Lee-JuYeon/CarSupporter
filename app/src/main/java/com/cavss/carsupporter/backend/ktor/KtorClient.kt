package com.cavss.carsupporter.backend.ktor

import android.util.Log
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

object KtorClient {

    //JSON 설정
    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    val HTTP_CLIENT = HttpClient{

        // JSON Setting
        install(JsonFeature){
            serializer = KotlinxSerializer(json = json)
        }

        // Logging Setting
        install(Logging){
            logger = object : Logger {
                override fun log(message: String) {
                    Log.e("mException", "KTorClient, HTTP_CLIENT, LoggingSetting // API LOG : ${message}")
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout){
            requestTimeoutMillis = 15_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 15_000
        }

        // 기본적인 api 호출시 넣는 것들 즉 기본 세팅
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

}