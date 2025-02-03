package com.x3mads.demo.ads

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.etermax.xmediator.core.api.entities.LoadResult
import com.etermax.xmediator.core.api.entities.ShowError
import com.x3mads.android.xmediator.core.api.RewardedAds
import com.x3mads.android.xmediator.core.api.XMediatorAds

object RewardedHelper {
    private const val TAG = "DemoApp:Rewarded"
    val RewLoaded = MutableLiveData<Boolean>()
    var notifiedEvent: (message: String) -> Unit = {}

    init {
        RewLoaded.value = false
    }

    // Use on initCallback or as soon as possible after X3M initialization
    fun loadRewardedAd(placementId: String) {
        Log.d(TAG, "Loading Rewarded")
        XMediatorAds.Rewarded.load(placementId)
    }

    // Use on your OnCreate
    fun registerListener() {
        Log.d(TAG, "Registering Rewarded listener")
        XMediatorAds.Rewarded.addListener(listener)
    }

    // Use on your OnDestroy
    fun unregisterListener() {
        Log.d(TAG, "Unregistering Rewarded listener")
        XMediatorAds.Rewarded.removeListener(listener)
    }

    fun showRewarded(activity: Activity, adSpace: String) {
        if (XMediatorAds.Rewarded.isReady())
            activity.runOnUiThread { XMediatorAds.Rewarded.show(activity, adSpace) }
        else {
            notifiedEvent("Rewarded not ready")
            Log.e("DemoView", "Error showing Rewarded, not ready")
        }
    }

    private val listener = object : RewardedAds.Listener {
        override fun onLoaded(placementId: String, loadResult: LoadResult) {
            RewLoaded.value = true
            Log.d("DemoView", "Rewarded loaded. Placement $placementId, result: $loadResult")
        }

        override fun onDismissed(placementId: String) {
            notifiedEvent("Should resume app")
            Log.d("DemoView", "Rewarded dismissed for placementId: $placementId")
        }

        override fun onEarnedReward(placementId: String) {
            Log.d("DemoView", "Rewarded earned for placementId: $placementId")
        }

        override fun onFailedToShow(placementId: String, showError: ShowError) {
            notifiedEvent("Should resume app")
            Log.d("DemoView", "Rewarded failed to show $placementId, ${showError.message}")
        }

        override fun onShowed(placementId: String) {
            Log.d("DemoView", "Rewarded shown for placementId: $placementId")
        }
    }
}