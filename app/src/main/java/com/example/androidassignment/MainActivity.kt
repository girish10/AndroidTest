package com.example.androidassignment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.ButterKnife
import com.example.androidassignment.adapter.FeedsAdapter
import com.example.androidassignment.models.JsonFeedRow
import com.example.androidassignment.utils.CheckInternet
import com.example.androidassignment.viewModel.FeedViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author girishsharma
 * Main class for calling the api and displaying the data on the UI
 * */

class MainActivity : FragmentActivity(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var feedVm: FeedViewModel

    /**
     * Initialize controls and variables
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        swipeRefreshMain.setOnRefreshListener(this)
    }


    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    /**
     * Method for network check, Controls Visibility toggle
     * and loader handling
     * */
    override fun onRefresh() {
        if (CheckInternet.networkOn(this)) {
            toggleVisibility(recylerViewMain, txtViewMainScreen)
            swipeRefreshMain.isRefreshing = true
            fetchFeed()
        } else {
            swipeRefreshMain.isRefreshing = false
            toggleVisibility(txtViewMainScreen, recylerViewMain)
            Toast.makeText(
                this,
                resources.getString(R.string.textNetwork),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Method for making network call and on response calling loadFeedRows()
     * to show data in RecyclerView
     * */
    private fun fetchFeed() {
        val factory = FeedViewModel.Factory(this.application)
        feedVm = ViewModelProviders.of(this, factory).get(FeedViewModel::class.java)
        feedVm.fetchFeed()
        feedVm.feedObserver!!.observe(this, Observer { jsonFeed ->
            if (jsonFeed != null) {
                titleTextMain.text = jsonFeed.title
                loadFeedRows(jsonFeed.arrayFeedRows)
            }
            swipeRefreshMain.isRefreshing = false
        })
    }

    /**
     * Method to show data in the RecyclerView
     * if(no data) show (msg)
     * */
    private fun loadFeedRows(arrayFeedRows: List<JsonFeedRow>) {
        if (arrayFeedRows.isNotEmpty()) {
            toggleVisibility(recylerViewMain, txtViewMainScreen)
            val rowsAdapter = FeedsAdapter(arrayFeedRows, this)
            val decoration = DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
            recylerViewMain.layoutManager = LinearLayoutManager(this)
            recylerViewMain.addItemDecoration(decoration)
            recylerViewMain.adapter = rowsAdapter
        } else {
            toggleVisibility(txtViewMainScreen, recylerViewMain)
            txtViewMainScreen.text = resources.getString(R.string.textNoData)
        }
    }

    /**
     * Method to toggle Visibility of controls
     * */
    private fun toggleVisibility(view: View, hideView: View) {
        hideView.visibility = View.GONE
        view.visibility = View.VISIBLE
    }
}