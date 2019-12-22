@file:Suppress("DEPRECATION")

package com.example.androidassignment.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author girishsharma
 * Class for checking network connectivity
 * */
class CheckInternet {

    companion object {
        fun networkOn(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
    }
}
