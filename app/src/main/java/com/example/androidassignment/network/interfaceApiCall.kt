package com.example.androidassignment.network

import com.example.androidassignment.models.jsonFeed
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author girishsharma
 *
 * interface for API calls*/
interface interfaceApiCall {
    @GET("facts.json")
    fun fetchFeed(): Call<jsonFeed>
}
