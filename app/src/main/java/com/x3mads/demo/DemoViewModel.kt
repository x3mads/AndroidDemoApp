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
import com.appharbr.sdk.engine.AdSdk
import com.appharbr.sdk.engine.AppHarbr
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.x3mads.demo.ads.AppOpenHelper
import com.x3mads.demo.ads.BannerHelper
import com.x3mads.demo.ads.InterstitialHelper
import com.x3mads.demo.ads.RewardedHelper
import com.x3mads.demo.ads.XMediatorHelper
import com.x3mads.demo.ads.XMediatorHelper.cmpEnabled
import com.x3mads.demo.ads.XMediatorHelper.fakeEeaRegion

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

    private var notifiedEvent: (message: String) -> Unit = {}

    init {
        _isInitialized.value = false
        notifiedEvent = {
            _onMessage.value = it
        }
    }

    fun onInitButtonClick(activity: Activity) {
        val appKey = XMediatorHelper.getAppKey()
        if (appKey == null) {
            Toast.makeText(activity, "Select a mediator first", Toast.LENGTH_SHORT).show()
            return
        }

        XMediatorHelper.initialize(
            activity = activity,
            onInitComplete = {
                _isInitialized.value = true
            }
        )
    }

    fun onShowBanner(activity: Activity, container: ViewGroup) {
        XMediatorHelper.showBanner(activity, container, adSpace)
    }

    fun onShowItt(activity: Activity) {
        XMediatorHelper.showInterstitial(activity, adSpace)
    }

    fun onShowRew(activity: Activity) {
        XMediatorHelper.showRewarded(activity, adSpace)
    }

    fun onShowApo(activity: Activity) {
        XMediatorHelper.showAppOpen(activity, adSpace)
    }

    fun onMediatorSelected(mediator: String) {
        when (mediator) {
            "X3M" -> {
                XMediatorHelper.changeMediator(
                    appKey = x3mAppKey,
                    bannerPlacementId = x3mBannerPlacementId,
                    interstitialPlacementId = x3mInterstitialPlacementId,
                    rewardedPlacementId = x3mRewardedPlacementId,
                    appOpenPlacementId = null
                )
            }

            "MAX" -> {
                XMediatorHelper.changeMediator(
                    appKey = maxAppKey,
                    bannerPlacementId = maxBannerPlacementId,
                    interstitialPlacementId = maxInterstitialPlacementId,
                    rewardedPlacementId = maxRewardedPlacementId,
                    appOpenPlacementId = maxAppOpenPlacementId
                )
            }

            "LEVEL PLAY" -> {
                XMediatorHelper.changeMediator(
                    appKey = lpAppKey,
                    bannerPlacementId = lpBannerPlacementId,
                    interstitialPlacementId = lpInterstitialPlacementId,
                    rewardedPlacementId = lpRewardedPlacementId,
                    appOpenPlacementId = null
                )
            }

            "GOOGLE ADS" -> {
                XMediatorHelper.changeMediator(
                    appKey = admobAppKey,
                    bannerPlacementId = admobBannerPlacementId,
                    interstitialPlacementId = admobInterstitialPlacementId,
                    rewardedPlacementId = admobRewardedPlacementId,
                    appOpenPlacementId = admobAppOpenPlacementId
                )
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
        XMediatorHelper.showCmpForm(activity)
    }

    fun onResetCmp(context: Context) {
        XMediatorHelper.resetCmp(context)
    }

    fun isCmpProviderAvailable(context: Context): Boolean {
        return XMediatorHelper.isCmpProviderAvailable(context)
    }

    fun onCustomMediatorSelected(context: Context, onComplete: () -> Unit) {
        var tempAppKey: String? = null
        var tempBannerPlacementId: String? = null
        var tempInterstitialPlacementId: String? = null
        var tempRewardedPlacementId: String? = null
        var tempAppOpenPlacementId: String? = null

        val inputFields = listOf(
            "App Key" to { value: String? -> tempAppKey = value },
            "Banner Placement ID" to { value: String? -> tempBannerPlacementId = value },
            "Interstitial Placement ID" to { value: String? -> tempInterstitialPlacementId = value },
            "Rewarded Placement ID" to { value: String? -> tempRewardedPlacementId = value },
            "App Open Placement ID" to { value: String? -> tempAppOpenPlacementId = value },
        )

        val inputLayouts = inputFields.map { (label, _) ->
            TextInputLayout(context).apply {
                hint = label
                addView(TextInputEditText(context).apply {
                    setText("")
                })
            }
        }

        showCustomMediatorDialog(context, inputLayouts, inputFields, onComplete) {
            XMediatorHelper.changeMediator(
                appKey = tempAppKey,
                bannerPlacementId = tempBannerPlacementId,
                interstitialPlacementId = tempInterstitialPlacementId,
                rewardedPlacementId = tempRewardedPlacementId,
                appOpenPlacementId = tempAppOpenPlacementId
            )
        }
    }

    fun onResume() {
        _isInitialized.value = XMediatorHelper.isInitialized.get()
    }

    fun onDebuggingSuiteButtonClick(activity: Activity) {
        XMediatorHelper.openDebuggingSuite(activity)
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
        inputFields: List<Pair<String, (String?) -> Unit>>,
        onComplete: () -> Unit,
        onSave: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle("Custom Mediator Settings")
            .setView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(32, 16, 32, 0)
                inputs.forEach { addView(it) }
            })
            .setPositiveButton("OK") { _, _ ->
                inputFields.forEachIndexed { index, (_, setter) ->
                    setter(inputs[index].editText?.text?.toString())
                }
                onSave()
                onComplete()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun onAppHarbrIntegrationDashboardButtonClick() {
        if (!AppHarbr.isInitialized()) {
            Log.w("DemoApp:AppHarbr", "AppHarbr not initialized. Please initialize it first.")
            return
        }
        val selectedMediation = when (XMediatorHelper.getAppKey()) {
            maxAppKey -> AdSdk.MAX
            admobAppKey -> AdSdk.ADMOB
            lpAppKey -> AdSdk.LEVELPLAY
            else -> {
                AdSdk.NONE
            }
        }
        if (selectedMediation == AdSdk.NONE) {
            Log.w("DemoApp:AppHarbr", "Attempting to launch integration dashboard for unknown mediation.")
            return
        }
        AppHarbr.launchIntegrationDashboard(selectedMediation)
    }
}