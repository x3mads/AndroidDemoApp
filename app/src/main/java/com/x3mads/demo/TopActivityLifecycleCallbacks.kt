package com.x3mads.demo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.etermax.xmediator.core.utils.logging.Category
import com.etermax.xmediator.core.utils.logging.XMediatorLogger
import java.lang.ref.WeakReference

internal object TopActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private var currentActivity = WeakReference<Activity>(null)
    private var lastDestroyActivity = WeakReference<Activity>(null)

    val topActivity: Activity?
        get() = currentActivity.get()

    val lastActivity: Activity?
        get() = lastDestroyActivity.get()

    fun registerWith(activity: Activity) {
        try {
            Log.i("CurrentActivity", "registerWith: ${activity.javaClass.name}")
            activity.application.unregisterActivityLifecycleCallbacks(this)
            currentActivity = WeakReference(activity)
            activity.application.registerActivityLifecycleCallbacks(this)
        } catch (e: Throwable) {
            Log.i("CurrentActivity", "Failed to register activity lifecycle callbacks: ${e.message}")
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i("CurrentActivity", "onActivityCreated: ${activity.javaClass.name}")
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = WeakReference(activity)
        Log.i("CurrentActivity", "onActivityStarted: ${activity.javaClass.name}")
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = WeakReference(activity)
        Log.i("CurrentActivity", "onActivityResumed: ${activity.javaClass.name}")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i("CurrentActivity", "onActivityPaused: ${activity.javaClass.name}")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i("CurrentActivity", "onActivityStopped: ${activity.javaClass.name}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.i("CurrentActivity", "onActivitySaveInstanceState: ${activity.javaClass.name}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        lastDestroyActivity = WeakReference(activity)
        Log.i("CurrentActivity", "onActivityDestroyed: ${activity.javaClass.name}")
    }


}