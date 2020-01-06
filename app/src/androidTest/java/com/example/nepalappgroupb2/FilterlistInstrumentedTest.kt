package com.example.nepalappgroupb2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.nepalappgroupb2.Recipe.RecipeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterlistInstrumentedTest{
@get:Rule
var activityRule: ActivityTestRule<RecipeActivity> = ActivityTestRule(RecipeActivity::class.java)

    @Test
    fun input_to_Livedata(){
        onView(withId(R.id.searchWordFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.searchWordFragment)).perform(clearText(),typeText( "abcdDCBA8"))

        onView(withId(R.id.searchWordFragment)).check(matches(withText(activityRule.activity.getSearchWord().getValue())) )

        activityRule.activity.finish()

    }

}