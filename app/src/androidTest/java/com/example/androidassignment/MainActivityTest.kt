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
import android.net.ConnectivityManager
import org.mockito.Mockito
import android.net.NetworkInfo
import com.android.dx.command.Main
import com.example.androidassignment.utils.CheckInternet
import com.example.androidassignment.utils.CheckInternet.Companion.networkOn
import org.mockito.MockitoAnnotations


/**
 * @author girishsharma
 * Class for UI testing to check the screen state with network or without network
 * */

@RunWith(JUnit4::class)
class MainActivityTest {

    private var activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    val connectivityManager = Mockito.mock(ConnectivityManager::class.java)
    val networkInfo = Mockito.mock(NetworkInfo::class.java)

    /**
     * UI Test to check screen state when data is available
     */
    @Test
    fun checkRecyclerViewFeeds() {
        activityTestRule.launchActivity(Intent())
        if(networkInfo.isConnected) {
            val recyclerView =
                activityTestRule.activity.findViewById(R.id.recylerViewMain) as RecyclerView
            val itemsCount = recyclerView.adapter?.itemCount
            Espresso.onView(ViewMatchers.withId(R.id.recylerViewMain)).check(matches(isDisplayed()))
            assertEquals(
                itemsCount,
                activityTestRule.activity.feedVm.feedObserver?.value?.arrayFeedRows?.size
            )
        }
    }

    /**
     * UI Test to check screen state when there is no wifi/data(no internet connectivity)
     * assumption: wifi/ mobile data is disabled
     */
    @Test
    fun checkNoInternetMessage() {
        activityTestRule.launchActivity(Intent())
        Mockito.`when`(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI))
            .thenReturn(networkInfo);

        // Here we can Mock network check for different scenarios
        Mockito.`when`(networkInfo.isConnected).thenReturn(true)
        if (!networkInfo.isConnected) {
            Espresso.onView(ViewMatchers.withId(R.id.txtViewMainScreen))
                .check(matches(isDisplayed()))
        }
    }
}