package com.x3mads.demo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etermax.xmediator.core.api.entities.CMPDebugGeography
import com.etermax.xmediator.core.api.entities.CMPDebugSettings
import com.etermax.xmediator.core.api.entities.ConsentInformation
import com.etermax.xmediator.core.api.entities.InitSettings
import com.etermax.xmediator.core.api.entities.UserProperties
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.x3mads.android.xmediator.core.api.XMediatorAds
import com.x3mads.demo.ads.AppOpenHelper
import com.x3mads.demo.ads.BannerHelper
import com.x3mads.demo.ads.InterstitialHelper
import com.x3mads.demo.ads.RewardedHelper
import kotlin.reflect.KMutableProperty0

private const val x3mAppKey = "4-16"
private const val x3mBannerPlacementId = "4-16/91"
private const val x3mInterstitialPlacementId = "4-16/93"
private const val x3mRewardedPlacementId = "4-16/96"

private const val maxAppKey = "V148L42DB1"
private const val maxBannerPlacementId = "V142DR9L2247MG"
private const val maxInterstitialPlacementId = "V142DRJLE1G5XX"
private const val maxRewardedPlacementId = "V142DR4LW2MT4B"
private const val maxAppOpenPlacementId = "V14JHR4ZKL86RTC2"

private const val admobAppKey = "V148L48DBJ"
private const val admobBannerPlacementId = "V14JHR4Z2LKRFYNP"
private const val admobInterstitialPlacementId = "V14JHR28KLVMZGXJ"
private const val admobRewardedPlacementId = "V14JHR282L889BY3"
private const val admobAppOpenPlacementId = "V14JHR283LMV0WYT"

private const val lpAppKey = "V148L42DB8"
private const val lpBannerPlacementId = "V142DR2LD0QYR1"
private const val lpInterstitialPlacementId = "V142DR1L7WJN07"
private const val lpRewardedPlacementId = "V142DR8L1DP5ND"

class DemoViewModel : ViewModel() {
    val isBanLoaded: LiveData<Boolean> get() = BannerHelper.BanLoaded
    val isIttLoaded: LiveData<Boolean> get() = InterstitialHelper.IttLoaded
    val isRewLoaded: LiveData<Boolean> get() = RewardedHelper.RewLoaded
    val isApoLoaded: LiveData<Boolean> get() = AppOpenHelper.ApoLoaded
    val onMessage: LiveData<String> get() = _onMessage

    private val _isInitialized = MutableLiveData<Boolean>()
    val isInitialized: LiveData<Boolean> get() = _isInitialized

    private val _onMessage = MutableLiveData<String>()
    private val adSpace: String = "MainActivity"

    private var appKey: String? = null
    private var bannerPlacementId: String? = null
    private var interstitialPlacementId: String? = null
    private var rewardedPlacementId: String? = null
    private var appOpenPlacementId: String? = null
    private var fakeEeaRegion: Boolean = false
    private var cmpEnabled: Boolean = false
    private var notifiedEvent: (message: String) -> Unit = {}

    init {
        _isInitialized.value = false
        notifiedEvent = {
            _onMessage.value = it
        }
    }

    fun onInitButtonClick(activity: Activity) {
        val appKey = appKey
        if (appKey == null) {
            Toast.makeText(activity, "Select a mediator first", Toast.LENGTH_SHORT).show()
            return
        }

        XMediatorAds.startWith(
            activity = activity,
            appKey = appKey,
            initSettings = InitSettings(
                userProperties = UserProperties(
                    userId = "your-user-id",
                ),
                consentInformation = getConsentInformation(),
                verbose = true,
                test = true,
            ),
            initCallback = {
                _isInitialized.value = true
                loadApo()
                createBanner()
                loadItt()
                loadRew()
                Log.d("DemoView", "Initialization complete!")
            },
        )
    }

    private fun getConsentInformation(): ConsentInformation? {
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

    private fun createBanner() {
        bannerPlacementId?.let { BannerHelper.createBannerAd(it) }
    }

    private fun loadItt() {
        interstitialPlacementId?.let { InterstitialHelper.loadInterstitialAd(it) }
    }

    private fun loadRew() {
        rewardedPlacementId?.let { RewardedHelper.loadRewardedAd(it) }
    }

    private fun loadApo() {
        appOpenPlacementId?.let { AppOpenHelper.loadAppOpenAd(it) }
    }

    fun onShowBanner(container: ViewGroup) {
        bannerPlacementId?.let { BannerHelper.showBannerAd(it, container, adSpace) }
    }

    fun onShowItt(activity: Activity) {
        InterstitialHelper.showItt(activity, adSpace)
    }

    fun onShowRew(activity: Activity) {
        RewardedHelper.showRewarded(activity, adSpace)
    }

    fun onShowApo(activity: Activity) {
        AppOpenHelper.showApo(activity, adSpace)
    }

    fun onMediatorSelected(mediator: String) {
        Log.i("DemoView", "Selected mediator: $mediator")
        when (mediator) {
            "X3M" -> {
                appKey = x3mAppKey
                bannerPlacementId = x3mBannerPlacementId
                interstitialPlacementId = x3mInterstitialPlacementId
                rewardedPlacementId = x3mRewardedPlacementId
                // AppOpen not supported for X3M
            }

            "MAX" -> {
                appKey = maxAppKey
                bannerPlacementId = maxBannerPlacementId
                interstitialPlacementId = maxInterstitialPlacementId
                rewardedPlacementId = maxRewardedPlacementId
                appOpenPlacementId = maxAppOpenPlacementId
            }

            "LEVEL PLAY" -> {
                appKey = lpAppKey
                bannerPlacementId = lpBannerPlacementId
                interstitialPlacementId = lpInterstitialPlacementId
                rewardedPlacementId = lpRewardedPlacementId
                // AppOpen not supported for Level Play
            }

            "GOOGLE ADS" -> {
                appKey = admobAppKey
                bannerPlacementId = admobBannerPlacementId
                interstitialPlacementId = admobInterstitialPlacementId
                rewardedPlacementId = admobRewardedPlacementId
                appOpenPlacementId = admobAppOpenPlacementId
            }

            else -> {
                // Do nothing
            }
        }
    }

    fun onCmpEnabledChanged(checked: Boolean) {
        cmpEnabled = checked
    }

    fun onFakeEeaRegionChanged(checked: Boolean) {
        fakeEeaRegion = checked
    }

    fun onShowCmpForm(activity: Activity) {
        XMediatorAds.CMPProvider.showPrivacyForm(activity) { error ->
            if (error != null) {
                Log.d("PrivacyForm", "Error: $error")
            }

            Log.d("PrivacyForm", "showPrivacyForm complete!")
        }
    }

    fun onResetCmp(context: Context) {
        XMediatorAds.CMPProvider.reset(context)
    }

    fun isCmpProviderAvailable(context: Context): Boolean {
        return XMediatorAds.CMPProvider.isPrivacyFormAvailable(context)
    }

    fun onCustomMediatorSelected(context: Context, onComplete: () -> Unit) {
        val inputFields = listOf(
            "App Key" to ::appKey,
            "Banner Placement ID" to ::bannerPlacementId,
            "Interstitial Placement ID" to ::interstitialPlacementId,
            "Rewarded Placement ID" to ::rewardedPlacementId,
            "App Open Placement ID" to ::appOpenPlacementId,
        )

        val inputLayouts = inputFields.map { (label, property) ->
            TextInputLayout(context).apply {
                hint = label
                addView(TextInputEditText(context).apply {
                    setText(property.get() ?: "")
                })
            }
        }

        showCustomMediatorDialog(context, inputLayouts, inputFields, onComplete)
    }

    fun onDebuggingSuiteButtonClick(activity: Activity) {
        XMediatorAds.openDebuggingSuite(activity)
    }

    fun resetApp(demoActivity: DemoActivity) {
        val packageManager: PackageManager = demoActivity.packageManager
        val intent: Intent? = packageManager.getLaunchIntentForPackage(demoActivity.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        demoActivity.startActivity(intent)
        demoActivity.finish()
        Runtime.getRuntime().exit(0)
    }

    fun subscribeEvents() {
        BannerHelper.registerListener()
        InterstitialHelper.registerListener()
        RewardedHelper.registerListener()
        AppOpenHelper.registerListener()
        InterstitialHelper.notifiedEvent = notifiedEvent
        RewardedHelper.notifiedEvent = notifiedEvent
        AppOpenHelper.notifiedEvent = notifiedEvent
    }

    fun unSubscribeEvents() {
        BannerHelper.unregisterListener()
        InterstitialHelper.unregisterListener()
        RewardedHelper.unregisterListener()
        AppOpenHelper.unregisterListener()
        InterstitialHelper.notifiedEvent = {}
        RewardedHelper.notifiedEvent = {}
        AppOpenHelper.notifiedEvent = {}
    }

    private fun showCustomMediatorDialog(
        context: Context,
        inputs: List<TextInputLayout>,
        inputFields: List<Pair<String, KMutableProperty0<String?>>>,
        onComplete: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle("Custom Mediator Settings")
            .setView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(32, 16, 32, 0)
                inputs.forEach { addView(it) }
            })
            .setPositiveButton("OK") { _, _ ->
                inputFields.forEachIndexed { index, (_, property) ->
                    property.set(inputs[index].editText?.text?.toString())
                }
                onComplete()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}