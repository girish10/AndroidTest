package com.kotlin.girishsharma.myapplication.models

import com.google.gson.annotations.SerializedName

/**
 * @author girishsharma
 *
 * Model class for inner data fo the feed rows */
class jsonFeedRow {

    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("imageHref")
    var imageHref: String? = null
}
