package com.etermax.xmediator.mediation.google_ads.infrastructure

import com.etermax.xmediator.core.api.entities.HttpError
import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.core.error
import com.etermax.xmediator.core.domain.core.success
import com.etermax.xmediator.mediation.google_ads.domain.AdStringRepository
import com.etermax.xmediator.mediation.google_ads.domain.AdStringService
import com.etermax.xmediator.mediation.google_ads.infrastructure.network.AdStringApi
import com.etermax.xmediator.mediation.google_ads.infrastructure.network.RetrofitClient

internal class AdStringServiceDefault(
    private val logTag: String,
    private val adStringRepository: AdStringRepository = AdStringRepositoryDefault(RetrofitClient.client.create(AdStringApi::class.java)),
) : AdStringService {

    override fun getAdString(url: String, callback: (Either<HttpError, String>) -> Unit) {

        adStringRepository.getAdString(url) { result ->
            when (result) {
                is Either.Success -> {
                    val adString = result.value
                    callback.invoke(adString.success())
                }
                is Either.Error -> {
                    callback.invoke(result.error.error())
                }
            }
        }
    }
}