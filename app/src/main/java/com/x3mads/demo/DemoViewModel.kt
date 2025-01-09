package com.x3mads.demo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etermax.xmediator.core.api.Banner
import com.etermax.xmediator.core.api.entities.CMPDebugGeography
import com.etermax.xmediator.core.api.entities.CMPDebugSettings
import com.etermax.xmediator.core.api.entities.ConsentInformation
import com.etermax.xmediator.core.api.entities.ImpressionData
import com.etermax.xmediator.core.api.entities.InitSettings
import com.etermax.xmediator.core.api.entities.LoadResult
import com.etermax.xmediator.core.api.entities.ShowError
import com.etermax.xmediator.core.api.entities.UserProperties
import com.x3mads.android.xmediator.core.api.BannerAds
import com.x3mads.android.xmediator.core.api.InterstitialAds
import com.x3mads.android.xmediator.core.api.RewardedAds
import com.x3mads.android.xmediator.core.api.XMediatorAds

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
    private val _isIttLoaded = MutableLiveData<Boolean>()
    val isIttLoaded: LiveData<Boolean> get() = _isIttLoaded

    private val _isRewLoaded = MutableLiveData<Boolean>()
    val isRewLoaded: LiveData<Boolean> get() = _isRewLoaded

    private val _isBanLoaded = MutableLiveData<Boolean>()
    val isBanLoaded: LiveData<Boolean> get() = _isBanLoaded

    private val _isInitialized = MutableLiveData<Boolean>()
    val isInitialized: LiveData<Boolean> get() = _isInitialized

    private var appKey: String? = null
    private var bannerPlacementId: String? = null
    private var interstitialPlacementId: String? = null
    private var rewardedPlacementId: String? = null
    private var fakeEeaRegion: Boolean = false
    private var cmpEnabled: Boolean = false

    init {
        _isIttLoaded.value = false
        _isRewLoaded.value = false
        _isInitialized.value = false
        _isBanLoaded.value = false
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
                loadBanner()
                loadItt(activity)
                loadRew(activity)
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

    fun loadBanner() {
        Log.d("DemoView", "Banner onLoad")
        XMediatorAds.Banner.addListener(object : BannerAds.Listener {

            override fun onImpression(placementId: String, impressionData: ImpressionData) {
                Log.d("DemoView", "Banner impression ecpm: ${impressionData.ecpm}")
            }

            override fun onLoaded(placementId: String, loadResult: LoadResult) {
                _isBanLoaded.value = true
                Log.d("DemoView", "Banner loaded")
            }

            override fun onClicked(placementId: String) {
                Log.d("DemoView", "Banner clicked")
            }
        })
        val size = Banner.Size.Phone /* or Banner.Size.Tablet */
        bannerPlacementId?.let {
            XMediatorAds.Banner.create(it, size)
        }
    }

    fun onShowBanner(container: ViewGroup) {
        val placementId = bannerPlacementId
        val view = if (placementId != null) XMediatorAds.Banner.getView(placementId) else null
        if (view == null) {
            Log.e("DemoView", "Error showing banner, not created")
        } else {
            val parentView = view.parent as? ViewGroup
            if (parentView == container) return
            parentView?.removeView(view)
            container.addView(view)
        }
    }

    fun loadItt(activity: Activity) {
        interstitialPlacementId?.let {
            XMediatorAds.Interstitial.addListener(object : InterstitialAds.Listener {
                override fun onLoaded(placementId: String, loadResult: LoadResult) {
                    _isIttLoaded.value = true
                    Log.d("DemoView", "Interstitial loaded. Placement $placementId, result: $loadResult")
                }

                override fun onShowed(placementId: String) {
                    Log.d("DemoView", "Interstitial shown: $placementId.")
                }

                override fun onFailedToShow(placementId: String, showError: ShowError) {
                    Log.d("DemoView", "Interstitial failed to show $placementId, ${showError.message}")
                    Toast.makeText(activity, "Interstitial failed to show: ${showError.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onDismissed(placementId: String) {
                    Log.d("DemoView", "Interstitial dismissed for placementId: $placementId")
                }

                override fun onImpression(placementId: String, impressionData: ImpressionData) {
                    Log.d("DemoView", "Interstitial impression for placementId: $placementId, data: $impressionData")
                }

                override fun onClicked(placementId: String) {
                    Log.d("DemoView", "Interstitial clicked for placementId: $placementId")
                }
            })
            XMediatorAds.Interstitial.load(it)
        }
    }

    fun onShowItt(activity: Activity) {
        if (XMediatorAds.Interstitial.isReady())
            activity.runOnUiThread { XMediatorAds.Interstitial.show(activity) }
        else {
            Toast.makeText(activity, "Interstitial not ready", Toast.LENGTH_SHORT).show()
            Log.e("DemoView", "Error showing interstitial, not ready")
        }
    }

    fun loadRew(activity: Activity) {
        rewardedPlacementId?.let {
            XMediatorAds.Rewarded.addListener(object : RewardedAds.Listener {

                override fun onLoaded(placementId: String, loadResult: LoadResult) {
                    _isRewLoaded.value = true
                    Log.d("DemoView", "Rewarded loaded. Placement $placementId, result: $loadResult")
                }

                override fun onDismissed(placementId: String) {
                    Log.d("DemoView", "Rewarded dismissed for placementId: $placementId")
                }

                override fun onEarnedReward(placementId: String) {
                    Log.d("DemoView", "Rewarded earned for placementId: $placementId")
                }

                override fun onFailedToShow(placementId: String, showError: ShowError) {
                    Log.d("DemoView", "Rewarded failed to show $placementId, ${showError.message}")
                    Toast.makeText(activity, "Rewarded failed to show: ${showError.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onShowed(placementId: String) {
                    Log.d("DemoView", "Rewarded shown for placementId: $placementId")
                }
            })
            XMediatorAds.Rewarded.load(it)
        }
    }

    fun onShowRew(activity: Activity) {
        if (XMediatorAds.Rewarded.isReady())
            activity.runOnUiThread { XMediatorAds.Rewarded.show(activity) }
        else {
            Toast.makeText(activity, "Rewarded not ready", Toast.LENGTH_SHORT).show()
            Log.e("DemoView", "Error showing rewarded, not ready")
        }
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

        val inputs = inputFields.map { (label, _) ->
            EditText(context).apply {
                hint = label
            }
        }

        AlertDialog.Builder(context)
            .setTitle("Custom Mediator Settings")
            .setView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                inputs.forEach { addView(it) }
            })
            .setPositiveButton("OK", null) // We'll set the listener later
            .setNegativeButton("Cancel", null)
            .create()
            .apply {
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val allFieldsFilled = inputs.all { it.text.isNotEmpty() }
                        if (allFieldsFilled) {
                            inputFields.forEachIndexed { index, (_, property) ->
                                property.set(inputs[index].text.toString())
                            }
                            onComplete()
                            dismiss()
                        } else {
                            Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
            .show()
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
}