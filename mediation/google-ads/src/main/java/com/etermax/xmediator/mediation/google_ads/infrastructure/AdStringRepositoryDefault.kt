package com.etermax.xmediator.mediation.google_ads.infrastructure

import com.etermax.xmediator.core.api.entities.HttpError
import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.core.error
import com.etermax.xmediator.core.domain.core.success
import com.etermax.xmediator.mediation.google_ads.domain.AdStringRepository
import com.etermax.xmediator.mediation.google_ads.infrastructure.network.AdStringApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class AdStringRepositoryDefault(private val adStringApi: AdStringApi) :
    AdStringRepository {

    override fun getAdString(url: String, callback: (Either<HttpError, String>) -> Unit) {
        adStringApi.call(url).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (isValidResponse(response)) {
                    val vast = response.body()!!.string()
                    callback.invoke(vast.success())
                } else {
                    callback.invoke(HttpError.Unexpected("ad_string_http_error_${response.code()}").error())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, e: Throwable) {
                val message = e.message
                callback.invoke(HttpError.Unexpected("ad_string_$message").error())
            }
        })

    }

    private fun isValidResponse(response: Response<ResponseBody>) =
        response.isSuccessful && response.body() != null

}