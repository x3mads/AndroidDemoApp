package com.etermax.xmediator.mediation.google_ads

import android.app.Activity
import android.app.Application
import android.content.Context
import com.etermax.xmediator.core.domain.banner.BannerAdapter
import com.etermax.xmediator.core.domain.banner.BannerSize
import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.core.alsoOnSuccess
import com.etermax.xmediator.core.domain.core.map
import com.etermax.xmediator.core.domain.core.success
import com.etermax.xmediator.core.domain.mediation.MediationNetwork
import com.etermax.xmediator.core.domain.mediation.adapters.InterstitialAdapter
import com.etermax.xmediator.core.domain.mediation.errors.AdapterLoadError
import com.etermax.xmediator.core.domain.prebid.adapters.ServerBidderAdapter
import com.etermax.xmediator.core.domain.prebid.adapters.ServerBidderNetwork
import com.etermax.xmediator.core.domain.rewarded.RewardedAdapter
import com.etermax.xmediator.core.utils.asTypeOrDefault
import com.etermax.xmediator.mediation.google_ads.admob.GoogleAdsBannerAdapter
import com.etermax.xmediator.mediation.google_ads.admob.GoogleAdsInterstitialAdapter
import com.etermax.xmediator.mediation.google_ads.admob.GoogleAdsRewardedAdapter
import com.etermax.xmediator.mediation.google_ads.domain.entities.GoogleInitParams
import com.etermax.xmediator.mediation.google_ads.domain.entities.GoogleLoadParams
import com.etermax.xmediator.mediation.google_ads.infrastructure.AdStringServiceDefault
import com.google.android.gms.ads.AdFormat
import java.lang.ref.WeakReference

@Suppress("unused")
public class XMediatorGoogleAdsMediationNetwork : MediationNetwork, ServerBidderNetwork {

    override suspend fun isInitialized(params: Map<String, Any>, applicationContext: Context): Either<AdapterLoadError, Boolean> {
        return SingleInit.isInitialized()
    }

    override suspend fun initialize(
        params: Map<String, Any>,
        applicationContext: Context,
        activity: WeakReference<Activity?>
    ): Either<AdapterLoadError, Unit> {
        val initParams = GoogleInitParams.createFrom(params)
        setCOPPA(initParams.consent.isChildDirected)
        return SingleInit.initialize(applicationContext, initParams)
    }

    override suspend fun createInterstitialAdapter(
        params: Map<String, Any>,
        application: Application,
        activityWeakReference: WeakReference<Activity?>
    ): Either<AdapterLoadError, InterstitialAdapter> {
        return GoogleLoadParams.createFrom(params.asTypeOrDefault(emptyMap()))
            .alsoOnSuccess {
                setCOPPA(it.consent.isChildDirected)
                setTestMode(it.isTest, application)
                Unit.success()
            }
            .map {
                GoogleAdsInterstitialAdapter(
                    it,
                    activityWeakReference,
                    application,
                    adRequestResolver(it, activityWeakReference, "ITT"),
                )
            }
    }

    override suspend fun createRewardedAdapter(
        params: Map<String, Any>,
        application: Application,
        activityWeakReference: WeakReference<Activity?>
    ): Either<AdapterLoadError, RewardedAdapter> {
        return GoogleLoadParams.createFrom(params.asTypeOrDefault(emptyMap()))
            .alsoOnSuccess {
                setCOPPA(it.consent.isChildDirected)
                setTestMode(it.isTest, application)
                Unit.success()
            }
            .map {
                GoogleAdsRewardedAdapter(
                    it,
                    activityWeakReference,
                    application,
                    adRequestResolver(it, activityWeakReference, "REW"),
                )
            }
    }

    override suspend fun createBannerAdapter(
        bannerSize: BannerSize,
        params: Map<String, Any>,
        application: Application,
        activityWeakReference: WeakReference<Activity?>
    ): Either<AdapterLoadError, BannerAdapter> {

        return GoogleLoadParams.createFrom(params.asTypeOrDefault(emptyMap()))
            .alsoOnSuccess {
                setCOPPA(it.consent.isChildDirected)
                setTestMode(it.isTest, application)
                Unit.success()
            }
            .map {
                GoogleAdsBannerAdapter(
                    bannerSize,
                    it,
                    activityWeakReference,
                    application,
                    adRequestResolver(it, activityWeakReference, "BAN"),
                )
            }
    }

    private fun adRequestResolver(
        it: GoogleLoadParams,
        activityWeakReference: WeakReference<Activity?>,
        logTag: String,
    ) = AdRequestResolver(AdStringServiceDefault(logTag), it, activityWeakReference)

    override fun createInterstitialServerBidderAdapter(): Either<AdapterLoadError, ServerBidderAdapter> {
        return AdMobServerBidderAdapter(AdFormat.INTERSTITIAL).success()
    }

    override fun createVideoServerBidderAdapter(): Either<AdapterLoadError, ServerBidderAdapter> {
        return AdMobServerBidderAdapter(AdFormat.REWARDED).success()
    }

    override fun createBannerServerBidderAdapter(): Either<AdapterLoadError, ServerBidderAdapter> {
        return AdMobServerBidderAdapter(AdFormat.BANNER).success()
    }
}