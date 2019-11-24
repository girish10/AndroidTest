package com.example.androidassignment

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.ButterKnife
import com.example.androidassignment.adapter.FeedsAdapter
import com.example.androidassignment.models.jsonFeedRow
import com.example.androidassignment.utils.CheckInternet
import com.example.androidassignment.viewModel.feedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Duration

/**
 * @author girishsharma
 * Main class for calling the api and displaying the data on the UI
 * */

class MainActivity : FragmentActivity(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var feedVm: feedViewModel

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
                resources.getString(R.string.textNetwork).toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

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

    private fun fetchFeed() {
        val factory = feedViewModel.Factory(this.application);
        feedVm = ViewModelProviders.of(this, factory).get(feedViewModel::class.java)
        feedVm.fetchFeed()
        feedVm.feedObserver!!.observe(this, Observer { jsonFeed ->
            if (jsonFeed != null) {
                titleTextMain.text = jsonFeed.title
                loadFeedRows(jsonFeed.arrayFeedRows)
            }
            swipeRefreshMain.isRefreshing = false
        })
    }

    private fun loadFeedRows(arrayFeedRows: List<jsonFeedRow>) {
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

    private fun toggleVisibility(view: View, hideView: View) {
        hideView.visibility = View.GONE
        view.visibility = View.VISIBLE
    }
}