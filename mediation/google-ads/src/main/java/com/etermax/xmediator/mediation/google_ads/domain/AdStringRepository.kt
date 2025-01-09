package com.etermax.xmediator.mediation.google_ads.domain

import com.etermax.xmediator.core.api.entities.HttpError
import com.etermax.xmediator.core.domain.core.Either

internal interface AdStringRepository {
    fun getAdString(url: String, callback: (Either<HttpError, String>) -> Unit)
}