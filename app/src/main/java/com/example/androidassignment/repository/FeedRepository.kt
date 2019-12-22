package com.example.androidassignment.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidassignment.models.JsonFeed
import com.example.androidassignment.network.InterfaceApiCall
import com.example.androidassignment.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author girishsharma
 *
 * Class for LiveData to handle success and failures */

class FeedRepository private constructor() {

    val jsonFeedData: LiveData<JsonFeed>
        get() {
            val data = MutableLiveData<JsonFeed>()
            val rftClient: RetrofitClient? = RetrofitClient()
            val apiService = rftClient?.getClient(context)?.create(InterfaceApiCall::class.java)
            val call = apiService!!.fetchFeed()

            call.enqueue(object : Callback<JsonFeed> {
                override fun onResponse(call: Call<JsonFeed>, response: Response<JsonFeed>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<JsonFeed>, t: Throwable) {
                    data.value = null
                }
            })
            return data
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var ourInstance: FeedRepository? = null
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun setContext(con: Context) {
            context = con
        }

        @get:Synchronized
        val instance: FeedRepository?
            get() {
                if (ourInstance == null) {
                    ourInstance = FeedRepository()
                }
                return ourInstance
            }
    }
}