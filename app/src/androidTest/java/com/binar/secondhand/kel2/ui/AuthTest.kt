package com.binar.secondhand.kel2.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.binar.secondhand.kel2.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random
import kotlin.random.nextInt

@LargeTest
@RunWith(AndroidJUnit4::class)
class AuthTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun signInAndSignUpTest() {

        val randomNumber = System.currentTimeMillis()%100
        val email = "kel2${randomNumber}@mail.com"

        Thread.sleep(3000)
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.main_account), withContentDescription("Akun"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_main_fragment),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val materialButton = onView(
            allOf(
                withId(R.id.btn_login), withText("Sign In"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment_host),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialTextView = onView(
            allOf(
                withId(R.id.tv_signup), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    13
                )
            )
        )
        materialTextView.perform(scrollTo(), click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.et_nama),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nama_container),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText.perform(scrollTo(), replaceText("Coba"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.email_container),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText2.perform(scrollTo(), replaceText(email), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.et_passowrd),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.password_container),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText3.perform(scrollTo(), replaceText("000000"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.et_konfirmasi_passowrd),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.konfirmasi_password_container),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText4.perform(scrollTo(), replaceText("000000"), closeSoftKeyboard())


        val appCompatButton = onView(
            allOf(
                withId(R.id.btn_signup), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    11
                )
            )
        )
        Thread.sleep(1000)
        appCompatButton.perform(scrollTo(), click())
        Thread.sleep(5000)

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.et_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.email_container),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText5.perform(scrollTo(), replaceText(email), closeSoftKeyboard())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.et_masukkan_passowrd),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.password_container),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText6.perform(scrollTo(), replaceText("000000"), closeSoftKeyboard())

        Thread.sleep(3000)

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.btn_masuk), withText("Sign In"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    11
                )
            )
        )
        appCompatButton2.perform(scrollTo(), click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
