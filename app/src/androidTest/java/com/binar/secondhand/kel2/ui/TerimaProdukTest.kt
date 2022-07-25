package com.binar.secondhand.kel2.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
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
class TerimaProdukTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun terimaProdukTest() {
        Thread.sleep(3000)
        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val floatingActionButton3 = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton3.perform(click())

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
        Thread.sleep(1000)
        materialButton.perform(click())
        Thread.sleep(5000)

        val textInputEditText = onView(
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
        textInputEditText.perform(scrollTo(), replaceText("wahyu@mail.com"), closeSoftKeyboard())

        val textInputEditText2 = onView(
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
        textInputEditText2.perform(scrollTo(), replaceText("000000"), closeSoftKeyboard())

        pressBack()

        val appCompatButton = onView(
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
        appCompatButton.perform(scrollTo(), click())

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.main_sale_list), withContentDescription("Daftar Jual"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_main_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val tabView = onView(
            allOf(
                withContentDescription("Diminati"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tab_product_filter),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.rv_notification),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val materialButton2 = onView(
            allOf(
                withId(R.id.btn_terima), withText("Terima"),
                childAtPosition(
                    allOf(
                        withId(R.id.btn_container),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            6
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btn_kirim), withText("Hubungi via Whatsapp"),
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
        materialButton3.perform(click())
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
