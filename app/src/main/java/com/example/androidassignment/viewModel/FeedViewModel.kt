@file:Suppress("UNCHECKED_CAST")

package com.example.androidassignment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidassignment.models.JsonFeed
import com.example.androidassignment.repository.FeedRepository

/**
 * @author girishsharma
 *  ViewModel class
 * */
class FeedViewModel(application: Application) :AndroidViewModel(application){

    var feedObserver: LiveData<JsonFeed>? = null

    fun fetchFeed() {
        feedObserver = FeedRepository.instance!!.jsonFeedData
    }

    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FeedViewModel(application) as T
        }
    }
}