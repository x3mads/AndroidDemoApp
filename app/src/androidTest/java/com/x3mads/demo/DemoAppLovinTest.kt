package com.x3mads.demo

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.io.IOException


class DemoAppLovinTest {

    private lateinit var scenario: ActivityScenario<DemoActivity>

    @Before
    fun setUp() {
        // Start the activity before each test

        scenario = ActivityScenario.launch(DemoActivity::class.java)
        dismissAppCrashSystemDialogIfShown()
        onView(withId(R.id.btn_init)).perform(click())
        Thread.sleep(2_000)
    }

    @After
    fun tearDown() {
        // Finish the activity after each test
    }

    @Test
    fun testInitButtonClick() {
        onView(withId(R.id.btn_init)).perform(click())
        // Add assertions to verify the expected behavior after the Init button click
    }

    @Test
    fun bannerShouldLoadAndShow() {
        onView(withId(R.id.et_placement_id)).perform(replaceText("4-16/407"))
        Thread.sleep(1_000)
        onView(withId(R.id.btn_load_ban)).perform(click())

        Thread.sleep(15_000)

        onView(withId(R.id.btn_show_ban)).check(matches(isEnabled()))

        onView(withId(R.id.btn_show_ban)).perform(click())

        Thread.sleep(10_000)

        onView(withId(R.id.banner_footer)).check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun interstitialShouldLoadAndShow() {
        onView(withId(R.id.et_placement_id)).perform(replaceText("4-16/408"))
        Thread.sleep(1_000)
        onView(withId(R.id.btn_load_itt)).perform(click())
        Thread.sleep(15_000)
        onView(withId(R.id.btn_show_itt)).check(matches(isEnabled()))

        onView(withId(R.id.btn_show_itt)).perform(click())
        Thread.sleep(5_000)

        scenario.onActivity {
            val topActivity = it.getTopActivity()
            Assert.assertEquals("AppLovinFullscreenThemedActivity", topActivity?.javaClass?.simpleName)
            if (topActivity != it)
                topActivity?.finish()
        }

        Thread.sleep(2_000)
    }

    @Ignore("Rewarded test placement not working")
    @Test
    fun rewardedShouldLoadAndShow() {
        onView(withId(R.id.et_placement_id)).perform(replaceText("4-16/409"))
        Thread.sleep(1_000)
        onView(withId(R.id.btn_load_rew)).perform(click())
        Thread.sleep(15_000)
        onView(withId(R.id.btn_show_rew)).check(matches(isEnabled()))

        onView(withId(R.id.btn_show_rew)).perform(click())
        Thread.sleep(5_000)

        scenario.onActivity {
            val topActivity = it.getTopActivity()
            Assert.assertEquals("AppLovinFullscreenThemedActivity", topActivity?.javaClass?.simpleName)
            if (topActivity != it)
                topActivity?.finish()
        }

        Thread.sleep(2_000)
    }

    fun dismissAppCrashSystemDialogIfShown() {
        try {
            UiDevice
                .getInstance(InstrumentationRegistry.getInstrumentation())
                .executeShellCommand(
                    "am broadcast -a android.intent.action.CLOSE_SYSTEM_DIALOGS"
                )
        } catch (e: IOException) {
            println("Exception: $e")
        }
    }
}
