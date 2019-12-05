@file:Suppress("UNCHECKED_CAST")

package com.example.androidassignment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidassignment.models.jsonFeed
import com.example.androidassignment.repository.feedRepository

/**
 * @author girishsharma
 *  ViewModel class
 * */
class feedViewModel(application: Application) :AndroidViewModel(application){

    var feedObserver: LiveData<jsonFeed>? = null

    fun fetchFeed() {
        feedObserver = feedRepository.instance!!.jsonFeedData
    }

    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return feedViewModel(application) as T
        }
    }
}