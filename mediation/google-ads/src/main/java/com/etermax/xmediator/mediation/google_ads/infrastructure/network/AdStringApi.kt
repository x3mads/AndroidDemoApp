package com.etermax.xmediator.mediation.google_ads.infrastructure.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

internal interface AdStringApi {
    @GET
    fun call(@Url url: String): Call<ResponseBody>
}