package com.etermax.xmediator.mediation.google_ads

import com.google.android.gms.ads.ResponseInfo

internal fun ResponseInfo.subNetworkName(): String? {
    val adSourceName = loadedAdapterResponseInfo?.adSourceName
    if (adSourceName?.isEmpty() == true) return loadedAdapterResponseInfo?.adapterClassName
    return adSourceName
}