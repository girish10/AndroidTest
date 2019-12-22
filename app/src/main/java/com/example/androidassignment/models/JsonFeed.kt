package com.example.androidassignment.models

import com.google.gson.annotations.SerializedName

/**
 * @author girishsharma
 * Model Class for json feed main data */
class JsonFeed {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("rows")
    var arrayFeedRows: List<JsonFeedRow> = ArrayList()

}
