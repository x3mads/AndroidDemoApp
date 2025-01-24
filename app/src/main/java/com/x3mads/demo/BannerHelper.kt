package com.x3mads.demo

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.etermax.xmediator.core.api.Banner
import com.etermax.xmediator.core.api.entities.ImpressionData
import com.etermax.xmediator.core.api.entities.LoadResult
import com.x3mads.android.xmediator.core.api.BannerAds
import com.x3mads.android.xmediator.core.api.XMediatorAds

object BannerAdHelper {
    private const val TAG = "DemoApp:Banner"
    val BanLoaded = MutableLiveData<Boolean>()

    init {
        BanLoaded.value = false
    }

    // Use on initCallback or as soon as possible after X3M initialization
    fun createBannerAd(bannerAdPlacementId: String) {
        Log.d(TAG, "Creating banner")
        XMediatorAds.Banner.create(bannerAdPlacementId, Banner.Size.Phone)
    }

    // Use on your onCreate
    fun showBannerAd(bannerAdPlacementId: String, container: ViewGroup) {
        Log.d(TAG, "Showing banner")
        val view = XMediatorAds.Banner.getView(bannerAdPlacementId)
        if (view != null) {
            val parentView = view.parent as? ViewGroup
            if (parentView == container) return
            parentView?.removeView(view)
            container.addView(view)
        }
    }

    // Use on your OnCreate
    fun registerListener() {
        Log.d(TAG, "Registering banner listener")
        XMediatorAds.Banner.addListener(bannerListener)
    }

    // Use on your OnDestroy
    fun unregisterListener() {
        Log.d(TAG, "Unregistering banner listener")
        XMediatorAds.Banner.removeListener(bannerListener)
    }

    private val bannerListener = object : BannerAds.Listener {

        override fun onImpression(placementId: String, impressionData: ImpressionData) {
            Log.d(TAG, "Banner impression ecpm: ${impressionData.ecpm}")
        }

        override fun onLoaded(placementId: String, loadResult: LoadResult) {
            BanLoaded.value = true
            Log.d(TAG, "Banner loaded")
        }

        override fun onClicked(placementId: String) {
            Log.d(TAG, "Banner clicked")
        }
    }
}