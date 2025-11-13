package com.x3mads.demo.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.etermax.xmediator.core.api.entities.CMPDebugGeography
import com.etermax.xmediator.core.api.entities.CMPDebugSettings
import com.etermax.xmediator.core.api.entities.ConsentInformation
import com.etermax.xmediator.core.api.entities.InitSettings
import com.etermax.xmediator.core.api.entities.UserProperties
import com.x3mads.android.xmediator.core.api.XMediatorAds
import java.util.concurrent.atomic.AtomicBoolean

object XMediatorHelper {

    var fakeEeaRegion: Boolean = false
    var cmpEnabled: Boolean = false
    var isInitialized = AtomicBoolean(false)

    private var appKey: String? = "V148L48DBJ"
    private var bannerPlacementId: String? = "V14JHR4Z2LKRFYNP"
    private var interstitialPlacementId: String? = "V14JHR28KLVMZGXJ"
    private var rewardedPlacementId: String? = "V14JHR282L889BY3"
    private var appOpenPlacementId: String? = "V142DR8L1DP5ND"

    fun changeMediator(
        appKey: String?,
        bannerPlacementId: String?,
        interstitialPlacementId: String?,
        rewardedPlacementId: String?,
        appOpenPlacementId: String?
    ) {
        XMediatorHelper.appKey = appKey
        XMediatorHelper.bannerPlacementId = bannerPlacementId
        XMediatorHelper.interstitialPlacementId = interstitialPlacementId
        XMediatorHelper.rewardedPlacementId = rewardedPlacementId
        XMediatorHelper.appOpenPlacementId = appOpenPlacementId
    }

    fun getAppKey(): String? = appKey

    fun initialize(
        activity: Activity,
        onInitComplete: (() -> Unit)? = null
    ) {
        if (isInitialized.get()) {
            Log.w("XMediatorHelper", "Already initialized")
            return
        }

        val currentAppKey = appKey
        if (currentAppKey == null) {
            Log.e("XMediatorHelper", "Cannot initialize: appKey is null")
            return
        }

        XMediatorAds.setUserProperties(UserProperties(userId = "appharbr-test-userid"))
        XMediatorAds.startWith(
            activity = activity,
            appKey = currentAppKey,
            initSettings = InitSettings(
                consentInformation = getConsentInformation(fakeEeaRegion, cmpEnabled),
                verbose = true, // TODO: Remove on production
                test = true, // TODO: Remove on production
            ),
            initCallback = {
                isInitialized.set(true)
                onInitComplete?.invoke()
                Log.d("DemoView", "Initialization complete!")
            },
        )
    }

    fun showCmpForm(activity: Activity) {
        XMediatorAds.CMPProvider.showPrivacyForm(activity) { error ->
            if (error != null) {
                Log.d("PrivacyForm", "Error: $error")
            }

            Log.d("PrivacyForm", "showPrivacyForm complete!")
        }
    }

    fun resetCmp(context: Context) {
        XMediatorAds.CMPProvider.reset(context)
    }

    fun isCmpProviderAvailable(context: Context): Boolean {
        return XMediatorAds.CMPProvider.isPrivacyFormAvailable(context)
    }

    fun openDebuggingSuite(activity: Activity) {
        XMediatorAds.openDebuggingSuite(activity)
    }

    fun showBanner(activity: Activity, container: android.view.ViewGroup, adSpace: String) {
        if (initializeIfNeedIt(activity)) return

        bannerPlacementId?.let {
            BannerHelper.showBannerAd(it, container, adSpace)
        }
    }

    fun showInterstitial(activity: Activity, adSpace: String) {
        if (initializeIfNeedIt(activity)) return
        InterstitialHelper.showItt(activity, adSpace)
    }


    fun showRewarded(activity: Activity, adSpace: String) {
        if (initializeIfNeedIt(activity)) return
        RewardedHelper.showRewarded(activity, adSpace)
    }

    fun showAppOpen(activity: Activity, adSpace: String) {
        if (initializeIfNeedIt(activity)) return
        AppOpenHelper.showApo(activity, adSpace)
    }

    private fun initializeIfNeedIt(activity: Activity): Boolean {
        if (isInitialized.get().not()) {
            initialize(activity)
            return true
        }
        return false
    }

    fun loadAds() {
        bannerPlacementId?.let { BannerHelper.createBannerAd(it) }
        interstitialPlacementId?.let { InterstitialHelper.loadInterstitialAd(it) }
        rewardedPlacementId?.let { RewardedHelper.loadRewardedAd(it) }
        appOpenPlacementId?.let { AppOpenHelper.loadAppOpenAd(it) }
    }

    private fun getConsentInformation(fakeEeaRegion: Boolean, cmpEnabled: Boolean): ConsentInformation? {
        var consentInformation: ConsentInformation? = null
        var cmpDebugSettings: CMPDebugSettings? = null

        if (fakeEeaRegion)
            cmpDebugSettings = CMPDebugSettings(
                cmpDebugGeography = CMPDebugGeography.EEA
            )

        if (cmpEnabled)
            consentInformation = ConsentInformation(
                isCMPAutomationEnabled = true,
                cmpDebugSettings = cmpDebugSettings
            )
        return consentInformation
    }
}
