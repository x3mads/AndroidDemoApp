package com.etermax.xmediator.mediation.google_ads

import android.app.Activity
import android.os.Bundle
import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.core.error
import com.etermax.xmediator.core.domain.core.success
import com.etermax.xmediator.core.domain.mediation.errors.AdapterLoadError
import com.etermax.xmediator.mediation.google_ads.domain.AdStringService
import com.etermax.xmediator.mediation.google_ads.domain.entities.GoogleLoadParams
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import java.lang.ref.WeakReference

internal const val AD_STRING_RESOLVE_FAILURE_ERROR = 100101

internal class AdRequestResolver(
    private val adStringService: AdStringService,
    private val loadParams: GoogleLoadParams,
    private val activityWeakReference: WeakReference<Activity?>,
    private val adRequest: AdRequest.Builder = AdRequest.Builder(),
) {

    fun resolve(onResult: (adRequestResult: Either<AdapterLoadError, AdRequest>) -> Unit) {
        addCommonParams()
        when {
            loadParams.adStringUrl != null -> resolveAdStringUrl(loadParams.adStringUrl, onResult)
            loadParams.adString != null -> resolveAdString(loadParams.adString, onResult)
            else -> onResult.invoke(adRequest.build().success())
        }
    }

    private fun resolveAdStringUrl(adStringUrl: String, onResult: (adRequestResult: Either<AdapterLoadError, AdRequest>) -> Unit) {
        adStringService.getAdString(adStringUrl) {
            when (it) {
                is Either.Error -> onResult.invoke(
                    AdapterLoadError.RequestFailed(
                        adapterCode = AD_STRING_RESOLVE_FAILURE_ERROR,
                        errorName = it.error.message
                    ).error()
                )
                is Either.Success -> onResult.invoke(adRequest.apply { setAdString(it.value) }.build().success())
            }
        }
    }

    private fun resolveAdString(adString: String, onResult: (adRequestResult: Either<AdapterLoadError, AdRequest>) -> Unit) {
        onResult.invoke(adRequest.apply { setAdString(adString) }.build().success())
    }

    private fun addCommonParams() {
        adRequest
            .setRequestAgent(loadParams.mediationName)
        val bundle = Bundle().apply { addConsent(loadParams.consent, activityWeakReference) }

        if (loadParams.auctionId != null) {
            bundle.apply { putString("placement_req_id", loadParams.auctionId) }
        }
        adRequest.addNetworkExtrasBundle(AdMobAdapter::class.java, bundle)
    }
}