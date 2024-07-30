package com.example.aplikasiskripsi

import android.os.Handler
import android.os.Looper
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.CoreMatchers.not
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private lateinit var decorView: android.view.View

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testLocationEmptyToast() {
        onView(withId(R.id.btn_submit)).perform(click())
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withText("Lokasi kosong"))
                .inRoot(withDecorView(not((decorView))))
                .check(matches(isDisplayed()))
        }, 2000)
    }


    @Test
    fun testTextEmptyToast() {
        onView(withId(R.id.ed_text)).perform(
            ViewActions.typeText("wifi parkir kolam renang"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.btn_submit)).perform(click())
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withText("Lokasi kosong"))
                .inRoot(withDecorView(not((decorView))))
                .check(matches(isDisplayed()))
        }, 2000)
    }
}

