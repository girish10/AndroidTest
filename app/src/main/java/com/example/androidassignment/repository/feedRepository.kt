package com.example.androidassignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidassignment.models.jsonFeed
import com.example.androidassignment.network.interfaceApiCall
import com.example.androidassignment.network.retrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * @author girishsharma
 *
 * Class for LiveData to handle success and failures */

class feedRepository private constructor() {

    val jsonFeedData: LiveData<jsonFeed>
        get() {
            val data = MutableLiveData<jsonFeed>()
            val apiService = retrofitClient.getClient().create(interfaceApiCall::class.java)
            val call = apiService.fetchFeed()

            call.enqueue(object : Callback<jsonFeed> {
                override fun onResponse(call: Call<jsonFeed>, response: Response<jsonFeed>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<jsonFeed>, t: Throwable) {
                    data.value = null
                }
            })
            return data
        }

    companion object {
        private var ourInstance: feedRepository? = null
        @get:Synchronized
        val instance: feedRepository?
            get() {
                if (ourInstance == null) {
                    ourInstance = feedRepository()
                }
                return ourInstance
            }
    }
}