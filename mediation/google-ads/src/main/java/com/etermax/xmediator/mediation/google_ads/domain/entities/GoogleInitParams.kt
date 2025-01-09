package com.etermax.xmediator.mediation.google_ads.domain.entities

internal data class GoogleInitParams(
    val initializeAdapters: Boolean,
    val consent: Consent,
) {

    companion object {
        fun createFrom(params: Map<String, Any>): GoogleInitParams {
            val initializeAdapters = params["initialize_adapters"].toString().toBoolean() ||
                    params["enable_mediation_initialization"].toString().toBoolean()
            val consent = Consent.createFrom(params)
            return GoogleInitParams(initializeAdapters, consent)
        }
    }
}