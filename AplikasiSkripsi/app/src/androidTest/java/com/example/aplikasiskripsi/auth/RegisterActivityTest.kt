package com.example.aplikasiskripsi.auth

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.aplikasiskripsi.R
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class RegisterActivityTest {

    private val email = "aji@gmail.com"
    private val pass = "12345678"
    private val invalidEmail = "aji@gmail"
    private val invalidPass = "123456"
    private val newEmail = "yanto24@gmail.com"

    @Before
    fun setup() {
        ActivityScenario.launch(RegisterActivity::class.java)
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun test1EmptyEmail() {
        Espresso.onView(withId(R.id.et_email)).perform(ViewActions.typeText(""))
        Espresso.onView(withId(R.id.et_pass)).perform(ViewActions.typeText(pass))
        closeSoftKeyboard()
        Espresso.onView(withId(R.id.btn_reg)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.et_email))
            .check(matches(hasErrorText("Email harus diisi")))
    }

    @Test
    fun test2EmptyPassword() {
        Espresso.onView(withId(R.id.et_email)).perform(ViewActions.typeText(email))
        Espresso.onView(withId(R.id.et_pass)).perform(ViewActions.typeText(""))
        closeSoftKeyboard()
        Espresso.onView(withId(R.id.btn_reg)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.et_pass))
            .check(matches(hasErrorText("Password harus diisi")))
    }

    @Test
    fun test3InvalidEmail() {
        Espresso.onView(withId(R.id.et_email)).perform(ViewActions.typeText(invalidEmail))
        Espresso.onView(withId(R.id.et_pass)).perform(ViewActions.typeText(pass))
        closeSoftKeyboard()
        Espresso.onView(withId(R.id.btn_reg)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.et_email))
            .check(matches(hasErrorText("Email tidak valid")))
    }

    @Test
    fun test4InvalidPassword() {
        Espresso.onView(withId(R.id.et_email)).perform(ViewActions.typeText(email))
        Espresso.onView(withId(R.id.et_pass)).perform(ViewActions.typeText(invalidPass))
        closeSoftKeyboard()
        Espresso.onView(withId(R.id.btn_reg)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.et_pass))
            .check(matches(hasErrorText("Password min 8 karakter")))
    }

    @Test
    fun test5AlreadyEmail() {
        Espresso.onView(withId(R.id.et_email)).perform(ViewActions.typeText(email))
        Espresso.onView(withId(R.id.et_pass)).perform(ViewActions.typeText(pass))
        closeSoftKeyboard()
        Espresso.onView(withId(R.id.btn_reg)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withId(R.id.et_email))
            .check(matches(hasErrorText("Email sudah digunakan")))
    }

    @Test
    fun test6RegisterSuccess() {
        Espresso.onView(withId(R.id.et_email)).perform(ViewActions.typeText(newEmail))
        Espresso.onView(withId(R.id.et_pass)).perform(ViewActions.typeText(pass))
        closeSoftKeyboard()
        Espresso.onView(withId(R.id.btn_reg)).perform(ViewActions.click())
        Thread.sleep(2000)
        Intents.intended(IntentMatchers.hasComponent(LoginActivity::class.java.name))
    }
}