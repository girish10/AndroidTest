package com.kotlin.girishsharma.myapplication.models

import com.google.gson.annotations.SerializedName

/**
 * @author girishsharma
 * Model Class for json feed main data */
class jsonFeed {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("rows")
    var arrayFeedRows: List<jsonFeedRow> = ArrayList()

}
