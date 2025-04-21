package com.abhishek.myapplication

import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.abhishek.myapplication.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.abhishek.myapplication", appContext.packageName)
    }

    @Test
    fun testAppLaunchesSuccessfully() {
        // Check if the main activity is launched
        val activity = activityRule.activity
        assertNotNull("MainActivity is null", activity)
    }

    @Test
    fun testPackageManagerNotNull() {
        // Check if PackageManager is not null
        val packageManager = ApplicationProvider.getApplicationContext<Context>().packageManager
        assertNotNull("PackageManager is null", packageManager)
    }

    @Test
    fun testAppHasInternetPermission() {
        // Check if the app has Internet permission
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val permission = "android.permission.INTERNET"
        val res = appContext.checkCallingOrSelfPermission(permission)
        assertEquals("App does not have Internet permission", PackageManager.PERMISSION_GRANTED, res)
    }

    @Test
    fun testActivityTitle() {
        // Check if the activity title is set correctly
        val activity = activityRule.activity
        val title = activity.title.toString()
        assertEquals("Expected title not found", "My Application", title)
    }
}