package com.example.goofymenu.model

import com.google.gson.annotations.SerializedName

data class Options (
    @SerializedName("CatagoryName")
    val catagoryName: String,

    @SerializedName("SiteUrl")
    val siteURL: String,

    val child: List<Child>? = null
)

data class Child (
    val categoryName: String,

    @SerializedName("siteUrl")
    val siteURL: String
)