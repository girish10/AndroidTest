package com.example.androidassignment.models

import com.google.gson.annotations.SerializedName

/**
 * @author girishsharma
 *
 * Model class for inner data fo the feed rows */
class JsonFeedRow {

    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("imageHref")
    var imageHref: String? = null
}
