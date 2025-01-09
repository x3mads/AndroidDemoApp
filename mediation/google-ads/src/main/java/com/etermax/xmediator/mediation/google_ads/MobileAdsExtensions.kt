package com.etermax.xmediator.mediation.google_ads

import android.content.Context
import com.etermax.xmediator.core.utils.DeviceInformation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_FALSE
import com.google.android.gms.ads.RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE

internal fun setCOPPA(isChildDirected: Boolean?) {
    if (isChildDirected == null) return

    val requestConfiguration = MobileAds.getRequestConfiguration()
        .toBuilder()
        .setTagForChildDirectedTreatment(if (isChildDirected) TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE else TAG_FOR_CHILD_DIRECTED_TREATMENT_FALSE)
        .build()
    MobileAds.setRequestConfiguration(requestConfiguration)
}

internal fun setTestMode(isTest: Boolean, context: Context) {
    val testDeviceIds = arrayListOf<String>()
    testDeviceIds.takeIf { isTest }?.addAll(
        listOf(AdRequest.DEVICE_ID_EMULATOR, DeviceInformation(context).deviceId)
    )
    val requestConfiguration = MobileAds.getRequestConfiguration().toBuilder().setTestDeviceIds(testDeviceIds).build()
    MobileAds.setRequestConfiguration(requestConfiguration)
}