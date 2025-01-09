package com.etermax.xmediator.mediation.google_ads

import android.os.Bundle
import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.core.error
import com.etermax.xmediator.core.domain.core.success
import com.etermax.xmediator.core.domain.mediation.errors.AdapterLoadError
import com.etermax.xmediator.core.domain.prebid.adapters.ServerBidderAdapter
import com.etermax.xmediator.core.domain.prebid.entities.ServerBidderAdapterResponse
import com.etermax.xmediator.core.utils.wrapCallback
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdFormat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.query.QueryInfo
import com.google.android.gms.ads.query.QueryInfoGenerationCallback
import kotlin.coroutines.coroutineContext

internal open class AdMobServerBidderAdapter(private val adFormat: AdFormat) : ServerBidderAdapter {
    override suspend fun getServerParams(): Either<AdapterLoadError, ServerBidderAdapterResponse> =
        wrapCallback(coroutineContext) { safeContinuation ->
            SingleInit.weakApplicationContext.get()?.let {
                QueryInfo.generate(it, adFormat, getAdRequest(), object : QueryInfoGenerationCallback() {
                    override fun onSuccess(queryInfo: QueryInfo) {
                        safeContinuation.resume(ServerBidderAdapterResponse(serverParams = getServerParams(
                            queryInfo
                        )
                        ).success())
                    }

                    override fun onFailure(error: String) {
                        safeContinuation.resume(AdapterLoadError.RequestFailed(adapterCode = 100, errorName = error).error())
                    }
                })
            }
        }

    private fun getServerParams(queryInfo: QueryInfo) =
        mapOf("buyer_uid" to queryInfo.query, "is_hybrid_mode" to true)

    open fun getAdRequest(): AdRequest {
        val extras = Bundle().apply { putString("query_info_type", "requester_type_2") }
        return AdRequest.Builder()
            .setRequestAgent("gbid_for_inhouse")
            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            .build()
    }
}