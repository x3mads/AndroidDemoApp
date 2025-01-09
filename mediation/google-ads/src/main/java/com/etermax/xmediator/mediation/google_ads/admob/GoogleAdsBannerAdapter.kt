package com.etermax.xmediator.mediation.google_ads.admob

import android.app.Activity
import android.content.Context
import android.view.View
import com.etermax.xmediator.core.domain.banner.BannerAdapter
import com.etermax.xmediator.core.domain.banner.BannerSize
import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.fullscreen.listeners.AdapterLoadInfo
import com.etermax.xmediator.core.domain.fullscreen.listeners.AdapterShowListener
import com.etermax.xmediator.core.domain.fullscreen.listeners.LoadableListener
import com.etermax.xmediator.core.domain.mediation.errors.AdapterLoadError
import com.etermax.xmediator.mediation.google_ads.AdRequestResolver
import com.etermax.xmediator.mediation.google_ads.domain.entities.GoogleLoadParams
import com.etermax.xmediator.mediation.google_ads.resolveContext
import com.etermax.xmediator.mediation.google_ads.subNetworkName
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference

internal class GoogleAdsBannerAdapter(
    private val bannerSize: BannerSize,
    private val loadParams: GoogleLoadParams,
    private val activityWeakReference: WeakReference<Activity?>,
    private val applicationContext: Context,
    private val adRequestResolver: AdRequestResolver,
    private var mainScope: CoroutineScope? = null,
) : BannerAdapter {

    override var view: View? = null
    override var showListener: AdapterShowListener? = null
    override var loadListener: LoadableListener? = null
    override var networkImpressionAware: Boolean = true

    private var adView: AdView? = null

    override fun load() {
        adView = AdView(resolveContext(loadParams, activityWeakReference, applicationContext))
        adView?.apply {
            setAdSize(getSize())
            adUnitId = loadParams.adunitId
            adListener = this.adListener
        }
        adView?.adListener = adListener
        adRequestResolver.resolve { result ->
            when (result) {
                is Either.Success -> loadAd(result.value)
                is Either.Error -> loadListener?.onFailedToLoad(result.error)
            }
        }
    }

    private fun loadAd(adRequest: AdRequest) {
        adView?.loadAd(adRequest)
        view = adView
    }

    override fun destroy() {
        adView?.destroy()
        mainScope?.cancel()
    }

    private fun getSize(): AdSize {
        return when (bannerSize) {
            BannerSize.Phone -> AdSize.BANNER
            BannerSize.Tablet -> AdSize.LEADERBOARD
            BannerSize.Mrec -> AdSize.MEDIUM_RECTANGLE
        }
    }

    private val adListener = object : AdListener() {
        override fun onAdLoaded() {
            loadListener?.onLoaded(
                AdapterLoadInfo(
                creativeId = adView?.responseInfo?.responseId,
                subNetwork = adView?.responseInfo?.subNetworkName(),
            )
            )
        }

        override fun onAdFailedToLoad(adError: LoadAdError) {
            loadListener?.onFailedToLoad(AdapterLoadError.RequestFailed(adapterCode = adError.code, reason = adError.message))
        }

        override fun onAdClosed() {
            showListener?.onDismissed()
        }

        override fun onAdOpened() {
            showListener?.onClicked()
            showListener?.onShowed()
        }

        override fun onAdImpression() {
            showListener?.onNetworkImpression()
        }
    }


}