package com.x3mads.demo.ads

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.etermax.xmediator.core.api.entities.ImpressionData
import com.etermax.xmediator.core.api.entities.LoadResult
import com.etermax.xmediator.core.api.entities.ShowError
import com.x3mads.android.xmediator.core.api.InterstitialAds
import com.x3mads.android.xmediator.core.api.XMediatorAds

object InterstitialHelper {
    private const val TAG = "DemoApp:Interstitial"
    val IttLoaded = MutableLiveData<Boolean>()
    var notifiedEvent: (message: String) -> Unit = {}

    init {
        IttLoaded.value = false
    }

    // Use on initCallback or as soon as possible after X3M initialization
    fun loadInterstitialAd(placementId: String) {
        Log.d(TAG, "Loading interstitial")
        XMediatorAds.Interstitial.load(placementId)
    }

    // Use on your OnCreate
    fun registerListener() {
        Log.d(TAG, "Registering Interstitial listener")
        XMediatorAds.Interstitial.addListener(listener)
    }

    // Use on your OnDestroy
    fun unregisterListener() {
        Log.d(TAG, "Unregistering Interstitial listener")
        XMediatorAds.Interstitial.removeListener(listener)
    }

    fun showItt(activity: Activity, adSpace: String) {
        if (XMediatorAds.Interstitial.isReady())
            activity.runOnUiThread { XMediatorAds.Interstitial.show(activity, adSpace) }
        else {
            notifiedEvent("Interstitial not ready")
            Log.e("DemoView", "Error showing interstitial, not ready")
        }
    }

    private val listener = object : InterstitialAds.Listener {
        override fun onLoaded(placementId: String, loadResult: LoadResult) {
            IttLoaded.value = true
            Log.d(TAG, "Interstitial loaded. Placement $placementId, result: $loadResult")
        }

        override fun onShowed(placementId: String) {
            Log.d(TAG, "Interstitial shown: $placementId.")
        }

        override fun onFailedToShow(placementId: String, showError: ShowError) {
            notifiedEvent("Should resume app")
            Log.d(TAG, "Interstitial failed to show $placementId, ${showError.message}")
        }

        override fun onDismissed(placementId: String) {
            notifiedEvent("Should resume app")
            Log.d(TAG, "Interstitial dismissed for placementId: $placementId")
        }

        override fun onImpression(placementId: String, impressionData: ImpressionData) {
            Log.d(TAG, "Interstitial impression for placementId: $placementId, data: $impressionData")
        }

        override fun onClicked(placementId: String) {
            Log.d(TAG, "Interstitial clicked for placementId: $placementId")
        }
    }
}