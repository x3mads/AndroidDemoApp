package com.etermax.xmediator.mediation.google_ads

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.etermax.xmediator.mediation.google_ads.domain.entities.Consent
import java.lang.ref.WeakReference

internal fun Bundle.addConsent(
    consent: Consent,
    activityRef: WeakReference<Activity?>
) {
    addHasUserConsent(consent.hasUserConsent)
    addDoNotSell(consent.doNotSell, activityRef, consent.reset)
}

private fun Bundle.addHasUserConsent(hasUserConsent: Boolean?): Bundle {
    if (hasUserConsent != null && !hasUserConsent) {
        putString("npa", "1")
    }
    return this
}

private fun Bundle.addDoNotSell(doNotSell: Boolean?, activityRef: WeakReference<Activity?>, reset: Boolean?): Bundle {
    if (doNotSell != null && doNotSell) {
        putInt("rdp", 1)
        activityRef.get()?.getPreferences(Context.MODE_PRIVATE)?.edit()?.putInt("gad_rdp", 1)?.apply()
    } else if (reset != null && reset) {
        activityRef.get()?.getPreferences(Context.MODE_PRIVATE)?.edit()?.remove("gad_rdp")?.apply()
    }
    return this
}
