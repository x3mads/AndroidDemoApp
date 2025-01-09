package com.etermax.xmediator.mediation.google_ads

import android.app.Activity
import android.content.Context
import com.etermax.xmediator.mediation.google_ads.domain.entities.GoogleLoadParams
import java.lang.ref.WeakReference

internal fun resolveContext(
    loadParams: GoogleLoadParams,
    activityWeakReference: WeakReference<Activity?>,
    applicationContext: Context
): Context {
    return if (loadParams.useActivityContext) {
        activityWeakReference.get() ?: applicationContext
    } else applicationContext
}