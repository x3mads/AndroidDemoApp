package com.etermax.xmediator.mediation.google_ads.infrastructure.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit

internal object RetrofitClient {
    val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://adtechx3m.etermax.net/")
            .client(OkHttpClient())
            .build()
    }
}