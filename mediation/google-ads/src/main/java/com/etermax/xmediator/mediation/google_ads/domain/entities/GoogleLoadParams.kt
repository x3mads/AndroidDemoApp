package com.etermax.xmediator.mediation.google_ads.domain.entities

import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.core.error
import com.etermax.xmediator.core.domain.core.success
import com.etermax.xmediator.core.domain.mediation.errors.AdapterLoadError
import com.etermax.xmediator.core.utils.asTypeOrNull

internal data class GoogleLoadParams(
    val adunitId: String,
    val isTest: Boolean,
    val uuid: String?,
    val iid: String?,
    val partnerName: String?,
    val customTargeting: Map<String, String>? = null,
    val adString: String? = null,
    val adStringUrl: String? = null,
    val consent: Consent,
    val mediationName: String,
    val auctionId: String? = null,
    val useActivityContext: Boolean,
) {
    companion object {

        fun createFrom(params: Map<String, Any>): Either<AdapterLoadError, GoogleLoadParams> {
            val adunitId = params["adunit_id"] as? String
            val test = params["test"]?.toString()?.toBoolean() ?: false
            val uuid = params["ad_instance_uuid"] as? String
            val iid = params["ad_instance_iid"] as? String
            val adString = params["ad_string"] as? String
            val adStringUrl = params["ad_string_url"] as? String
            val partnerName = params["ad_instance_partner_name"] as? String
            val customTargeting: Map<String, String>? = params["custom_targeting"]?.asTypeOrNull()
            val mediationName = (params["mediation_name"] as? String?) ?: "X3M"
            val auctionId = params["auction_id"]?.toString()
            val initializeAdapters = params["initialize_adapters"].toString().toBoolean() || params["enable_mediation_initialization"].toString().toBoolean()
            val forceUseApplicationContext = params["adapter_admob_force_use_application_context"]?.toString()?.toBoolean() ?: true
            val useActivityContext = initializeAdapters && !forceUseApplicationContext

            return if (!adunitId.isNullOrBlank()) {
                GoogleLoadParams(
                    adunitId = adunitId,
                    isTest = test,
                    uuid = uuid,
                    iid = iid,
                    partnerName = partnerName,
                    customTargeting = customTargeting,
                    adString = adString,
                    adStringUrl = adStringUrl,
                    consent = Consent.createFrom(params),
                    mediationName = mediationName,
                    auctionId = auctionId,
                    useActivityContext = useActivityContext,
                ).success()
            } else {
                AdapterLoadError.InvalidConfiguration.error()
            }
        }
    }
}