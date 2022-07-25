package com.binar.secondhand.kel2.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
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
class TawarProdukTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun tawarProdukTest() {
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
//        Thread.sleep(1000)
//        materialButton.perform(click())
//        Thread.sleep(500)
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
//        textInputEditText.perform(
//            scrollTo(),
//            replaceText("testing@testing.com"),
//            closeSoftKeyboard()
//        )
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
//        appCompatButton.perform(scrollTo(), click())
        Thread.sleep(5000)

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.main_home), withContentDescription("Home"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_main_fragment),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.et_search),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.swipe_layout),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val textInputEditText3 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.et_search),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(
            replaceText("celana"),
            pressImeActionButton(),
            closeSoftKeyboard()
        )

//        // Klik logo search
//        val checkableImageButton = onView(
//            allOf(
//                withId(androidx.appcompat.R.id.text_input_end_icon),
//                childAtPosition(
//                    childAtPosition(
//                        withClassName(`is`("android.widget.LinearLayout")),
//                        1
//                    ),
//                    0
//                ),
//                isDisplayed()
//            )
//        )
//        checkableImageButton.perform(click())

        Thread.sleep(3000)

        val recyclerView = onView(
            allOf(
                withId(R.id.rv_home_product),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    3
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        Thread.sleep(4000)

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.btn_tertarik), withText("Saya Tertarik dan Ingin Nego"),
                childAtPosition(
                    allOf(
                        withId(R.id.coordinator3),
                        childAtPosition(
                            withId(R.id.detail_product),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        Thread.sleep(4000)

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.etHargaTawar),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.textField),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("1"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.etHargaTawar), withText("Rp. 1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.textField),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("Rp. 10"))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.etHargaTawar), withText("Rp. 10"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.textField),
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
                withId(R.id.btn_kirim), withText("Kirim"),
                childAtPosition(
                    allOf(
                        withId(R.id.dialogBottom),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
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
