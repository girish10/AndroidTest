package com.example.androidassignment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.ButterKnife
import com.example.androidassignment.adapter.FeedsAdapter
import com.example.androidassignment.models.jsonFeedRow
import com.example.androidassignment.utils.CheckInternet
import com.example.androidassignment.viewModel.feedViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author girishsharma
 * Main class for calling the api and displaying the data on the UI
 * */

class MainActivity : FragmentActivity() , SwipeRefreshLayout.OnRefreshListener {

    lateinit var feedVm: feedViewModel

    override fun onRefresh() {
        swipeRefreshMain.isRefreshing = true
        if (CheckInternet.networkOn(this))
            fetchFeed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
    }


    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    private fun fetchFeed() {
        val factory = feedViewModel.Factory(this.application);
        feedVm = ViewModelProviders.of(this, factory).get(feedViewModel::class.java)
        feedVm.fetchFeed()
        feedVm.feedObserver!!.observe(this, Observer {
            titleTextMain.setText(it.title)
            loadFeedRows(it.arrayFeedRows)
            swipeRefreshMain.isRefreshing = false
        })
    }

    private fun loadFeedRows(arrayFeedRows: List<jsonFeedRow>) {
        val rowsAdapter = FeedsAdapter(arrayFeedRows, this)
        val decoration = DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL)
        recylerViewMain.layoutManager = LinearLayoutManager(this)
        recylerViewMain.addItemDecoration(decoration)
        recylerViewMain.adapter = rowsAdapter
    }
}
