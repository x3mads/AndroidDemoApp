package com.etermax.xmediator.mediation.google_ads

import android.content.Context
import com.etermax.xmediator.core.domain.core.Either
import com.etermax.xmediator.core.domain.core.success
import com.etermax.xmediator.core.domain.mediation.errors.AdapterLoadError
import com.etermax.xmediator.core.utils.wrapCallback
import com.etermax.xmediator.mediation.google_ads.domain.entities.GoogleInitParams
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.ref.WeakReference
import kotlin.coroutines.coroutineContext

internal object SingleInit {

    var weakApplicationContext: WeakReference<Context> = WeakReference(null)

    private var initialized: Boolean = false
    private val mutex = Mutex()

    fun isInitialized(): Either<AdapterLoadError, Boolean> = initialized.success()

    suspend fun initialize(applicationContext: Context, initParams: GoogleInitParams): Either<AdapterLoadError, Unit> {
        mutex.withLock {
            if (initialized) return Unit.success()
            weakApplicationContext = WeakReference(applicationContext.applicationContext)
            return wrapCallback(coroutineContext) { safeContinuation ->
                if (!initParams.initializeAdapters) {
                    MobileAds.disableMediationAdapterInitialization(applicationContext)
                }
                MobileAds.initialize(applicationContext) {
                    initialized = true
                    safeContinuation.resume(Unit.success())
                }
            }
        }

    }
}