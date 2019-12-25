package com.example.androidassignment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import butterknife.ButterKnife
import com.example.androidassignment.repository.FeedRepository
import com.example.androidassignment.viewModel.FeedViewModel
import com.example.androidassignment.viewModel.MainFragment

/**
 * @author girishsharma
 * Main class for calling the api and displaying the data on the UI
 * */

class MainActivity : FragmentActivity() {

    lateinit var feedVm: FeedViewModel

    /**
     * Initialize controls and variables
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        FeedRepository.setContext(this)

        // create the fragment
        if (savedInstanceState == null) {
            val mainFragment = MainFragment(this, this.application)
            // add the fragment
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, mainFragment, "main_fragment").commit()
        } else {
            supportFragmentManager.findFragmentByTag("main_fragment") as MainFragment
        }
    }
}