package com.x3mads.demo

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etermax.xmediator.core.api.Banner
import com.etermax.xmediator.core.api.entities.ImpressionData
import com.etermax.xmediator.core.api.entities.InitSettings
import com.etermax.xmediator.core.api.entities.LoadResult
import com.etermax.xmediator.core.api.entities.ShowError
import com.etermax.xmediator.core.api.entities.UserProperties
import com.x3mads.android.xmediator.core.api.BannerAds
import com.x3mads.android.xmediator.core.api.InterstitialAds
import com.x3mads.android.xmediator.core.api.RewardedAds
import com.x3mads.android.xmediator.core.api.XMediatorAds

private const val bannerPlacementId = "4-16/407"
private const val interstitialPlacementId = "4-16/408"
private const val rewardedPlacementId = "4-16/409"

class DemoViewModel : ViewModel() {
    // MutableLiveData to observe the state of load actions
    private val _isIttLoaded = MutableLiveData<Boolean>()
    val isIttLoaded: LiveData<Boolean> get() = _isIttLoaded

    private val _isRewLoaded = MutableLiveData<Boolean>()
    val isRewLoaded: LiveData<Boolean> get() = _isRewLoaded

    private val _isBanLoaded = MutableLiveData<Boolean>()
    val isBanLoaded: LiveData<Boolean> get() = _isBanLoaded

    private val _isInitialized = MutableLiveData<Boolean>()
    val isInitialized: LiveData<Boolean> get() = _isInitialized

    private var currentPlacementId: String? = null

    init {
        // Initialize flags
        _isIttLoaded.value = false
        _isRewLoaded.value = false
        _isInitialized.value = false
        _isBanLoaded.value = false
    }

    fun onInitButtonClick(activity: Activity) {
        XMediatorAds.startWith(
            activity = activity,
            appKey = "4-16",
            initSettings = InitSettings(
                userProperties = UserProperties(
                    userId = "automationUser",
                ),
                verbose = true,
                test = true,
            ),
            initCallback = {
                _isInitialized.value = true
                Log.d("DemoView", "Initialization complete!")
            },
        )
    }

    fun onLoadBanner() {
        // Handle Load Banner button click
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
        currentPlacementId?.let {
            XMediatorAds.Banner.create(it, size)
        }
    }

    fun onShowBanner(container: ViewGroup) {
        val placementId = currentPlacementId
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

    fun onLoadItt() {
        currentPlacementId?.let {
            XMediatorAds.Interstitial.addListener(interstitialListener)
            XMediatorAds.Interstitial.load(it)
        }
    }

    fun onShowItt(activity: Activity) {
        currentPlacementId?.let {
            if (XMediatorAds.Interstitial.isReady(it))
                XMediatorAds.Interstitial.show(it, activity)
            else Log.e("DemoView", "Error showing interstitial, not ready")
        }
    }

    fun onLoadRew() {
        currentPlacementId?.let {
            XMediatorAds.Rewarded.addListener(rewardedListener)
            XMediatorAds.Rewarded.load(it)
        }
    }

    fun onShowRew(activity: Activity) {
        currentPlacementId?.let {
            if (XMediatorAds.Rewarded.isReady(it))
                XMediatorAds.Rewarded.show(it, activity)
            else Log.e("DemoView", "Error showing rewarded, not ready")
        }
    }

    fun setPlacementId(placementId: String) {
        currentPlacementId = placementId
    }

    private val interstitialListener = object : InterstitialAds.Listener {
        override fun onLoaded(placementId: String, loadResult: LoadResult) {
            _isIttLoaded.value = true
            Log.d("DemoView", "Interstitial loaded. Placement $placementId, result: $loadResult")
        }

        override fun onShowed(placementId: String) {
            Log.d("DemoView", "Interstitial shown: $placementId.")
        }

        override fun onFailedToShow(placementId: String, showError: ShowError) {
            Log.d("DemoView", "Interstitial failed to show $placementId, ${showError.message}")
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
    }

    private val rewardedListener = object : RewardedAds.Listener {

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
        }

        override fun onShowed(placementId: String) {
            Log.d("DemoView", "Rewarded shown for placementId: $placementId")
        }
    }
}