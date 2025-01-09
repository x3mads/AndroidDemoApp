package com.etermax.xmediator.mediation.google_ads.admob

import android.app.Activity
import android.content.Context
import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.fullscreen.listeners.AdapterLoadInfo
import com.etermax.xmediator.core.domain.mediation.errors.AdapterLoadError
import com.etermax.xmediator.core.domain.mediation.errors.AdapterShowError
import com.etermax.xmediator.core.domain.rewarded.RewardedAdapter
import com.etermax.xmediator.mediation.google_ads.AdRequestResolver
import com.etermax.xmediator.mediation.google_ads.domain.entities.GoogleLoadParams
import com.etermax.xmediator.mediation.google_ads.resolveContext
import com.etermax.xmediator.mediation.google_ads.subNetworkName
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference

internal class GoogleAdsRewardedAdapter(
    private val loadParams: GoogleLoadParams,
    private val activityWeakReference: WeakReference<Activity?>,
    private val applicationContext: Context,
    private val adRequestResolver: AdRequestResolver,
    private var mainScope: CoroutineScope? = null,
) : RewardedAdapter() {

    private var rewardedAd: RewardedAd? = null

    override fun isReady(): Boolean {
        val isReady = rewardedAd != null
        return isReady
    }

    override fun onLoad() {
        adRequestResolver.resolve { result ->
            when (result) {
                is Either.Success -> {
                    val context = resolveContext(loadParams, activityWeakReference, applicationContext)
                    RewardedAd.load(context, loadParams.adunitId, result.value, loadListener())
                }
                is Either.Error -> loadListener?.onFailedToLoad(result.error)
            }
        }
    }

    override fun onShowed(activity: Activity) {
        rewardedAd?.fullScreenContentCallback = presentListener()
        rewardedAd?.show(activity, rewardListener())
    }

    override fun onDestroy() {
        rewardedAd = null
        mainScope?.cancel()
    }

    private fun loadListener(): RewardedAdLoadCallback = object : RewardedAdLoadCallback() {
        override fun onAdLoaded(rewardedAd: RewardedAd) {
            this@GoogleAdsRewardedAdapter.rewardedAd = rewardedAd
            loadListener?.onLoaded(
                AdapterLoadInfo(
                    creativeId = rewardedAd.responseInfo.responseId,
                    subNetwork = rewardedAd.responseInfo.subNetworkName(),
                )
            )
        }

        override fun onAdFailedToLoad(adError: LoadAdError) {
            loadListener?.onFailedToLoad(AdapterLoadError.RequestFailed(adapterCode = adError.code, reason = adError.message))
        }
    }

    private fun presentListener(): FullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            showListener?.onFailedToShow(AdapterShowError.ShowFailed(adapterCode = adError.code))
        }

        override fun onAdShowedFullScreenContent() {
            showListener?.onShowed()
        }

        override fun onAdImpression() {
            showListener?.onNetworkImpression()
        }

        override fun onAdDismissedFullScreenContent() {
            showListener?.onDismissed()
        }

        override fun onAdClicked() {
            showListener?.onClicked()
        }
    }

    private fun rewardListener() = OnUserEarnedRewardListener {
        rewardListener?.onEarnReward()
    }

}
