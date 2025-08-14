package com.x3mads.demo.ads

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.etermax.xmediator.core.api.entities.ImpressionData
import com.etermax.xmediator.core.api.entities.LoadResult
import com.etermax.xmediator.core.api.entities.ShowError
import com.x3mads.android.xmediator.core.api.AppOpenAds
import com.x3mads.android.xmediator.core.api.XMediatorAds

object AppOpenHelper {
    private const val TAG = "DemoApp:AppOpen"
    val ApoLoaded = MutableLiveData<Boolean>()
    var notifiedEvent: (message: String) -> Unit = {}

    init {
        ApoLoaded.value = false
    }

    // Use on initCallback or as soon as possible after X3M initialization
    fun loadAppOpenAd(placementId: String) {
        Log.d(TAG, "Loading app open")
        XMediatorAds.AppOpen.load(placementId)
    }

    // Use on your OnCreate
    fun registerListener() {
        Log.d(TAG, "Registering AppOpen listener")
        XMediatorAds.AppOpen.addListener(listener)
    }

    // Use on your OnDestroy
    fun unregisterListener() {
        Log.d(TAG, "Unregistering AppOpen listener")
        XMediatorAds.AppOpen.removeListener(listener)
    }

    fun showApo(activity: Activity, adSpace: String) {
        if (XMediatorAds.AppOpen.isReady())
            XMediatorAds.AppOpen.show(activity, adSpace)
        else {
            notifiedEvent("AppOpen not ready")
            Log.e("DemoView", "Error showing app open, not ready")
        }
    }

    private val listener = object : AppOpenAds.Listener {
        override fun onLoaded(placementId: String, loadResult: LoadResult) {
            ApoLoaded.value = true
            Log.d(TAG, "AppOpen loaded. Placement $placementId, result: $loadResult")
        }

        override fun onShowed(placementId: String) {
            Log.d(TAG, "AppOpen shown: $placementId.")
        }

        override fun onFailedToShow(placementId: String, showError: ShowError) {
            notifiedEvent("Should resume app")
            Log.d(TAG, "AppOpen failed to show $placementId, ${showError.message}")
        }

        override fun onDismissed(placementId: String) {
            notifiedEvent("Should resume app")
            Log.d(TAG, "AppOpen dismissed for placementId: $placementId")
        }

        override fun onImpression(placementId: String, impressionData: ImpressionData) {
            Log.d(TAG, "AppOpen impression for placementId: $placementId, data: $impressionData")
        }

        override fun onClicked(placementId: String) {
            Log.d(TAG, "AppOpen clicked for placementId: $placementId")
        }
    }
}