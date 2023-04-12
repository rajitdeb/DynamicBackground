package com.rajit.dynamicbackground.data.model

import com.google.gson.annotations.SerializedName

// containing the individual model items
data class ResultSingleItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url:String
)
