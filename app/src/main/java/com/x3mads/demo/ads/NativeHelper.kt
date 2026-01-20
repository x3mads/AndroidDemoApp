package com.x3mads.demo.ads

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.etermax.xmediator.core.api.entities.ImpressionData
import com.etermax.xmediator.core.api.entities.LoadResult
import com.etermax.xmediator.core.api.entities.ShowError
import com.etermax.xmediator.core.domain.native.NativeComponent
import com.etermax.xmediator.core.domain.native.NativeLayout
import com.etermax.xmediator.core.domain.native.NativeRenderConfiguration
import com.x3mads.android.xmediator.core.api.NativeAds
import com.x3mads.android.xmediator.core.api.XMediatorAds
import com.x3mads.demo.R
import com.x3mads.demo.ads.NativeHelper.NativeLayoutType.COMPACT
import com.x3mads.demo.ads.NativeHelper.NativeLayoutType.STANDARD

object NativeHelper {
    private const val TAG = "DemoApp:Native"
    val NativeLoaded = MutableLiveData<Boolean>()
    private var nativeLayoutType = STANDARD

    init {
        NativeLoaded.value = false
    }

    // Use on initCallback or as soon as possible after X3M initialization
    fun loadNativeAd(placementId: String) {
        Log.d(TAG, "Creating native")
        XMediatorAds.Native.load(placementId)
    }

    // Use on your onCreate
    fun showNativeAd(placementId: String, container: ViewGroup, adSpace: String) {
        Log.d(TAG, "Showing native")
        XMediatorAds.Native.show(
            container = container,
            placementId = placementId,
            configuration = NativeRenderConfiguration(
                layout = NativeLayout.LayoutResource(
                    layoutResId = if (nativeLayoutType == STANDARD)
                        R.layout.native_ad_layout_standard else R.layout.native_ad_layout_compact,
                    componentsResId = mapOf(
                        NativeComponent.ICON_IMAGE_VIEW to R.id.native_ad_icon,
                        NativeComponent.TITLE_LABEL to R.id.native_ad_title,
                        NativeComponent.ADVERTISER_LABEL to R.id.native_ad_advertiser,
                        NativeComponent.MEDIA_VIEW to R.id.native_ad_media,
                        NativeComponent.BODY_LABEL to R.id.native_ad_body,
                        NativeComponent.CALL_TO_ACTION_BUTTON to R.id.native_ad_call_to_action,
                        NativeComponent.AD_BADGE to R.id.native_ad_badge,
                        NativeComponent.STAR_RATING_VIEW to R.id.native_ad_star_rating,
                        NativeComponent.PRICE_LABEL to R.id.native_ad_price,
                        NativeComponent.STORE_LABEL to R.id.native_ad_store,
                    )
                )
            ),
            adSpace = adSpace
        )
    }

    // Use on your OnCreate
    fun registerListener() {
        Log.d(TAG, "Registering native listener")
        XMediatorAds.Native.addListener(nativeListener)
    }

    // Use on your OnDestroy
    fun unregisterListener() {
        Log.d(TAG, "Unregistering native listener")
        XMediatorAds.Native.removeListener(nativeListener)
    }

    private val nativeListener = object : NativeAds.Listener {

        override fun onImpression(placementId: String, impressionData: ImpressionData) {
            Log.d(TAG, "Native impression ecpm: ${impressionData.ecpm}")
        }

        override fun onLoaded(placementId: String, loadResult: LoadResult) {
            NativeLoaded.value = true
            Log.d(TAG, "Native loaded for placementId: $placementId, loadResult: $loadResult")
        }

        override fun onClicked(placementId: String) {
            Log.d(TAG, "Native clicked for placementId: $placementId")
        }

        override fun onShowed(placementId: String) {
            Log.d(TAG, "Native on showed for placementId: $placementId")
        }

        override fun onFailedToShow(placementId: String, showError: ShowError) {
            Log.d(TAG, "Native failed to show: $placementId, showError: $showError")
        }
    }

    private enum class NativeLayoutType {
        STANDARD,
        COMPACT
    }
}