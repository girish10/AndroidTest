package com.example.androidassignment.viewModel

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.androidassignment.R
import com.example.androidassignment.adapter.FeedsAdapter
import com.example.androidassignment.models.JsonFeedRow
import com.example.androidassignment.utils.CheckInternet
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment constructor(private val ctx: Context, private val application: Application) :
    Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var feedVm: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // retain this fragment
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        if (CheckInternet.networkOn(ctx)) {
            toggleVisibility(recyclerViewMain, txtViewMainScreen)
            swipeRefreshMain.isRefreshing = true
            fetchFeed()
        } else {
            swipeRefreshMain.isRefreshing = false
            toggleVisibility(txtViewMainScreen, recyclerViewMain)
            Toast.makeText(
                ctx,
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
            toggleVisibility(recyclerViewMain, txtViewMainScreen)
            val rowsAdapter = FeedsAdapter(arrayFeedRows, ctx)
            val decoration = DividerItemDecoration(
                ctx,
                DividerItemDecoration.VERTICAL
            )
            recyclerViewMain.layoutManager = LinearLayoutManager(ctx)
            recyclerViewMain.addItemDecoration(decoration)
            recyclerViewMain.adapter = rowsAdapter
        } else {
            toggleVisibility(txtViewMainScreen, recyclerViewMain)
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