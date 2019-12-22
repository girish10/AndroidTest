package com.example.androidassignment.network

import android.content.ContentValues
import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @author girishsharma
 *
 * Class for network communication such as API Calls
 */
class RetrofitClient {
    private var ctx: Context? = null

    companion object {
        private var retrofit: Retrofit? = null
        private const val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"
    }

    fun getClient(mContext: Context): Retrofit? {
        ctx = mContext
        val logging = HttpLoggingInterceptor()

        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            // add your other interceptors …
            .addInterceptor(logging)
            .addInterceptor(provideOfflineCacheInterceptor())
            .addNetworkInterceptor(provideCacheInterceptor())
            .cache(provideCache())

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }
        return retrofit
    }

    private fun provideCache(): Cache? {
        var cache: Cache? = null

        try {
            cache = Cache(
                File(ctx!!.cacheDir, "http-cache"),
                (10 * 1024 * 1024).toLong()
            ) // 10 MB
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Could not create Cache!")
        }

        return cache
    }

    private fun provideCacheInterceptor(): Interceptor {

        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                val originalResponse = chain.proceed(request)
                val cacheControl = originalResponse.header("Cache-Control")

                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                        "no-cache"
                    ) ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
                ) {

                    val cc = CacheControl.Builder()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()

                    request = request.newBuilder()
                        .cacheControl(cc)
                        .build()

                    return chain.proceed(request)

                } else {
                    return originalResponse
                }
            }
        }
    }

    private fun provideOfflineCacheInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                return try {
                    chain.proceed(chain.request())
                } catch (e: Exception) {


                    val cacheControl = CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()

                    val offlineRequest = chain.request().newBuilder()
                        .cacheControl(cacheControl)
                        .build()
                    chain.proceed(offlineRequest)
                }
            }
        }
    }
}
