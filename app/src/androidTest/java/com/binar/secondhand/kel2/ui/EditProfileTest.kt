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

@LargeTest
@RunWith(AndroidJUnit4::class)
class EditProfileTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun editProfileTest2() {
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

//        val materialButton = onView(
//            allOf(
//                withId(R.id.btn_login), withText("Sign In"),
//                childAtPosition(
//                    childAtPosition(
//                        withId(R.id.main_fragment_host),
//                        0
//                    ),
//                    8
//                ),
//                isDisplayed()
//            )
//        )
//
//        Thread.sleep(1000)
//        materialButton.perform(click())
//        Thread.sleep(1000)
//
//        val textInputEditText = onView(
//            allOf(
//                withId(R.id.et_email),
//                childAtPosition(
//                    childAtPosition(
//                        withId(R.id.email_container),
//                        0
//                    ),
//                    0
//                )
//            )
//        )
//        textInputEditText.perform(scrollTo(), replaceText("testing@testing.com"), closeSoftKeyboard())
//
//        val textInputEditText2 = onView(
//            allOf(
//                withId(R.id.et_masukkan_passowrd),
//                childAtPosition(
//                    childAtPosition(
//                        withId(R.id.password_container),
//                        0
//                    ),
//                    0
//                )
//            )
//        )
//        textInputEditText2.perform(scrollTo(), replaceText("000000"), closeSoftKeyboard())
//
//        val appCompatButton = onView(
//            allOf(
//                withId(R.id.btn_masuk), withText("Sign In"),
//                childAtPosition(
//                    childAtPosition(
//                        withClassName(`is`("android.widget.ScrollView")),
//                        0
//                    ),
//                    11
//                )
//            )
//        )
//        Thread.sleep(1000)
//        appCompatButton.perform(scrollTo(), click())
        Thread.sleep(5000)

        val linearLayout = onView(
            allOf(
                withId(R.id.container_ubah_akun),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardview),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        Thread.sleep(1000)

        val textInputEditText3 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.et_address),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText3.perform(scrollTo(), replaceText("Jalan baru kiri"))

        val textInputEditText4 = onView(
            allOf(
                withText("Jalan baru kiri"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.et_address),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.et_phone),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText5.perform(scrollTo(), replaceText("62812223334448"))

        val textInputEditText6 = onView(
            allOf(
                withText("62812223334448"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.et_phone),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    13
                )
            )
        )
        materialButton2.perform(scrollTo(), click())
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
