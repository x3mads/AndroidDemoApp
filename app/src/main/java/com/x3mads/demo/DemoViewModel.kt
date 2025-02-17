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
import com.x3mads.demo.ads.BannerHelper
import com.x3mads.demo.ads.InterstitialHelper
import com.x3mads.demo.ads.RewardedHelper
import kotlin.reflect.KMutableProperty0

private const val x3mAppKey = "3-15"
private const val x3mBannerPlacementId = "3-15/28"
private const val x3mInterstitialPlacementId = "3-15/26"
private const val x3mRewardedPlacementId = "3-15/27"

private const val maxAppKey = "3-180"
private const val maxBannerPlacementId = "3-180/1150"
private const val maxInterstitialPlacementId = "3-180/1151"
private const val maxRewardedPlacementId = "3-180/1152"

private const val lpAppKey = "3-181"
private const val lpBannerPlacementId = "3-181/1153"
private const val lpInterstitialPlacementId = "3-181/1154"
private const val lpRewardedPlacementId = "3-181/1155"

class DemoViewModel : ViewModel() {
    val isBanLoaded: LiveData<Boolean> get() = BannerHelper.BanLoaded
    val isIttLoaded: LiveData<Boolean> get() = InterstitialHelper.IttLoaded
    val isRewLoaded: LiveData<Boolean> get() = RewardedHelper.RewLoaded
    val onMessage: LiveData<String> get() = _onMessage

    private val _isInitialized = MutableLiveData<Boolean>()
    val isInitialized: LiveData<Boolean> get() = _isInitialized

    private val _onMessage = MutableLiveData<String>()
    private val adSpace: String = "MainActivity"

    companion object{
        private var appKey: String? = null
        private var bannerPlacementId: String? = null
        private var interstitialPlacementId: String? = null
        private var rewardedPlacementId: String? = null
        private var fakeEeaRegion: Boolean = false
        private var cmpEnabled: Boolean = false
    }

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

    fun onShowBanner(container: ViewGroup) {
        bannerPlacementId?.let { BannerHelper.showBannerAd(it, container, adSpace) }
    }

    fun onShowItt(activity: Activity) {
        InterstitialHelper.showItt(activity, adSpace)
    }

    fun onShowRew(activity: Activity) {
        RewardedHelper.showRewarded(activity, adSpace)
    }

    fun onMediatorSelected(mediator: String) {
        Log.i("DemoView", "Selected mediator: $mediator")
        when (mediator) {
            "X3M" -> {
                appKey = x3mAppKey
                bannerPlacementId = x3mBannerPlacementId
                interstitialPlacementId = x3mInterstitialPlacementId
                rewardedPlacementId = x3mRewardedPlacementId
            }

            "MAX" -> {
                appKey = maxAppKey
                bannerPlacementId = maxBannerPlacementId
                interstitialPlacementId = maxInterstitialPlacementId
                rewardedPlacementId = maxRewardedPlacementId
            }

            "LEVEL PLAY" -> {
                appKey = lpAppKey
                bannerPlacementId = lpBannerPlacementId
                interstitialPlacementId = lpInterstitialPlacementId
                rewardedPlacementId = lpRewardedPlacementId
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
            "Rewarded Placement ID" to ::rewardedPlacementId
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
        InterstitialHelper.notifiedEvent = notifiedEvent
        RewardedHelper.notifiedEvent = notifiedEvent
    }

    fun unSubscribeEvents() {
        BannerHelper.unregisterListener()
        InterstitialHelper.unregisterListener()
        RewardedHelper.unregisterListener()
        InterstitialHelper.notifiedEvent = {}
        RewardedHelper.notifiedEvent = {}
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

    fun onResume(container: ViewGroup) {
        if (_isInitialized.value == true)
            bannerPlacementId?.let { BannerHelper.showBannerAd(it, container, adSpace) }
    }
}