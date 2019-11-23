package com.kotlin.girishsharma.myapplication.network

import com.kotlin.girishsharma.myapplication.models.jsonFeed

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
