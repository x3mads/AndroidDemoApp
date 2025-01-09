package com.etermax.xmediator.mediation.google_ads.domain.entities

private const val KEY_HAS_USER_CONSENT = "com_etermax_xmediator_has_user_consent"
private const val KEY_DO_NOT_SELL = "com_etermax_xmediator_do_not_sell"
private const val KEY_ECONOMIC_AREA = "com_etermax_xmediator_economic_area"
private const val KEY_IS_CHILD_DIRECTED = "com_x3mads_xmediator_is_child_directed"
private const val KEY_RESET_CONSENT = "reset_consent"

internal data class Consent(
    val hasUserConsent: Boolean?,
    val doNotSell: Boolean?,
    val isChildDirected: Boolean?,
    val economicArea: String?,
    val reset: Boolean?,
) {
    companion object {
        fun createFrom(params: Map<String, Any>): Consent {
            val hasUserConsent = (params[KEY_HAS_USER_CONSENT] as? Boolean?)
            val doNotSell = (params[KEY_DO_NOT_SELL] as? Boolean?)
            val isChildDirect = (params[KEY_IS_CHILD_DIRECTED] as? Boolean?)
            val economicArea = params[KEY_ECONOMIC_AREA]?.toString()
            val resetConsent = params[KEY_RESET_CONSENT] as? Boolean

            return Consent(hasUserConsent, doNotSell, isChildDirect, economicArea, resetConsent)
        }
    }
}