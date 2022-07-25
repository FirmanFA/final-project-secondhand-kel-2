package com.binar.secondhand.kel2.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.binar.secondhand.kel2.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PreviewAndTerbitkanProdukTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun previewAndTerbitkanProdukTest() {
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
                withId(R.id.main_sell), withContentDescription("Jual"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_main_fragment),
                        0
                    ),
                    2
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
                    3
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
        textInputEditText.perform(scrollTo(), replaceText("coba@mail.com"), closeSoftKeyboard())

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
        textInputEditText2.perform(scrollTo(), replaceText("111111"), closeSoftKeyboard())

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

        val textInputEditText3 = onView(
            childAtPosition(
                childAtPosition(
                    withId(R.id.et_name),
                    0
                ),
                0
            )
        )
        textInputEditText3.perform(scrollTo(), replaceText("kulkas"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            childAtPosition(
                childAtPosition(
                    withId(R.id.et_price),
                    0
                ),
                0
            )
        )
        textInputEditText4.perform(scrollTo(), replaceText("1"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withText("Rp. 1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.et_price),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText5.perform(scrollTo(), replaceText("Rp. 10"))

        val textInputEditText6 = onView(
            allOf(
                withText("Rp. 10"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.et_price),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(closeSoftKeyboard())

        val materialAutoCompleteTextView = onView(
            allOf(
                withId(R.id.auto_complete_tv),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.et_city),
                        0
                    ),
                    0
                )
            )
        )
        materialAutoCompleteTextView.perform(scrollTo(), click())

//        val materialTextView = onData(anything())
//            .inAdapterView(
//                childAtPosition(
//                    withClassName(`is`("android.widget.PopupWindow$PopupBackgroundView")),
//                    0
//                )
//            )
//            .atPosition(4)
//        materialTextView.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btn_add_category), withText("Tambah Kategori"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    10
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val materialCheckBox = onView(
            allOf(
                withId(R.id.cb_category), withText("Elektronik"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.rv_select_category),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialCheckBox.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btn_save), withText("Simpan"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val textInputEditText7 = onView(
            childAtPosition(
                childAtPosition(
                    withId(R.id.et_description),
                    0
                ),
                0
            )
        )
        textInputEditText7.perform(scrollTo(), replaceText("kulkas"), closeSoftKeyboard())

        pressBack()

        val appCompatImageView = onView(
            allOf(
                withId(R.id.iv_photo),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    14
                )
            )
        )
        appCompatImageView.perform(scrollTo(), click())

//        val linearLayout = onView(
//            allOf(
//                withId(androidx.appcompat.R.id.lytGalleryPick),
//                childAtPosition(
//                    childAtPosition(
//                        withId(androidx.appcompat.R.id.custom),
//                        0
//                    ),
//                    1
//                ),
//                isDisplayed()
//            )
//        )
//        linearLayout.perform(click())

//        val actionMenuItemView = onView(
//            allOf(
//                withId(androidx.appcompat.R.id.menu_crop), withContentDescription("Potong"),
//                childAtPosition(
//                    childAtPosition(
//                        withId(R.id.toolbar),
//                        2
//                    ),
//                    0
//                ),
//                isDisplayed()
//            )
//        )
//        actionMenuItemView.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.btn_preview), withText("Preview"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    15
                )
            )
        )
        materialButton4.perform(scrollTo(), click())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.btn_terbit), withText("Terbitkan"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_host),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())
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
