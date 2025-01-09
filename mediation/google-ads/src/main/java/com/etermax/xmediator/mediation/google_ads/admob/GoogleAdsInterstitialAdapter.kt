package com.etermax.xmediator.mediation.google_ads.admob

import android.app.Activity
import android.content.Context
import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.fullscreen.listeners.AdapterLoadInfo
import com.etermax.xmediator.core.domain.mediation.adapters.InterstitialAdapter
import com.etermax.xmediator.core.domain.mediation.errors.AdapterLoadError
import com.etermax.xmediator.core.domain.mediation.errors.AdapterShowError
import com.etermax.xmediator.mediation.google_ads.AdRequestResolver
import com.etermax.xmediator.mediation.google_ads.domain.entities.GoogleLoadParams
import com.etermax.xmediator.mediation.google_ads.resolveContext
import com.etermax.xmediator.mediation.google_ads.subNetworkName
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference

internal class GoogleAdsInterstitialAdapter(
    private val loadParams: GoogleLoadParams,
    private val activityWeakReference: WeakReference<Activity?>,
    private val applicationContext: Context,
    private val adRequestResolver: AdRequestResolver,
    private var mainScope: CoroutineScope? = null,
) : InterstitialAdapter() {

    private var interstitialAd: InterstitialAd? = null

    override fun isReady(): Boolean {
        val isReady = interstitialAd != null
        return isReady
    }

    override fun onLoad() {
        adRequestResolver.resolve { result ->
            when (result) {
                is Either.Success -> {
                    val context = resolveContext(loadParams, activityWeakReference, applicationContext)
                    InterstitialAd.load(context, loadParams.adunitId, result.value, loadListener())
                }
                is Either.Error -> loadListener?.onFailedToLoad(result.error)
            }
        }
    }

    override fun onShowed(activity: Activity) {
        interstitialAd?.fullScreenContentCallback = presentListener()
        interstitialAd?.show(activity)
    }

    override fun onDestroy() {
        interstitialAd = null
        mainScope?.cancel()
    }

    private fun loadListener(): InterstitialAdLoadCallback = object : InterstitialAdLoadCallback() {
        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            this@GoogleAdsInterstitialAdapter.interstitialAd = interstitialAd
            loadListener?.onLoaded(
                AdapterLoadInfo(
                    creativeId = interstitialAd.responseInfo.responseId,
                    subNetwork = interstitialAd.responseInfo.subNetworkName(),
                ))
        }

        override fun onAdFailedToLoad(error: LoadAdError) {
            loadListener?.onFailedToLoad(AdapterLoadError.RequestFailed(adapterCode = error.code, reason = error.message))
        }
    }

    private fun presentListener(): FullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            showListener?.onFailedToShow(AdapterShowError.ShowFailed(adapterCode = adError.code))
        }

        override fun onAdImpression() {
            showListener?.onNetworkImpression()
        }

        override fun onAdShowedFullScreenContent() {
            showListener?.onShowed()
        }

        override fun onAdDismissedFullScreenContent() {
            showListener?.onDismissed()
        }

        override fun onAdClicked() {
            showListener?.onClicked()
        }
    }

}
