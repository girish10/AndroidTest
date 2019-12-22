package com.example.androidassignment.network

import com.example.androidassignment.models.JsonFeed
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author girishsharma
 *
 * interface for API calls*/
interface InterfaceApiCall {
    @GET("facts.json")
    fun fetchFeed(): Call<JsonFeed>
}
