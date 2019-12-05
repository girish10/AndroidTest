package com.example.androidassignment

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.junit.Assert.assertEquals

/**
 * @author girishsharma
 * Class for UI testing to check the screen state with network or without network
 * */

@RunWith(JUnit4::class)
class MainActivityTest {

    private var activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    /**
     * UI Test to check screen state when data is available
     */
    @Test
    fun checkRecyclerViewFeeds() {
        activityTestRule.launchActivity(Intent())
        val recyclerView = activityTestRule.activity.findViewById(R.id.recylerViewMain) as RecyclerView
        val itemsCount = recyclerView.adapter?.itemCount
        Espresso.onView(ViewMatchers.withId(R.id.recylerViewMain)).check(matches(isDisplayed()))
        assertEquals(itemsCount, activityTestRule.activity.feedVm.feedObserver?.value?.arrayFeedRows?.size)
    }

    /**
     * UI Test to check screen state when there is no wifi/data(no internet connectivity)
     * assumption: wifi/ mobile data is disabled
     */
    @Test
    fun checkNoInternetMessage() {
        //TODO: Mock network
        activityTestRule.launchActivity(Intent())
        Espresso.onView(ViewMatchers.withId(R.id.txtViewMainScreen)).check(matches(isDisplayed()))
    }
}