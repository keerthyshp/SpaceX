package com.keerthy.spacex.model

import com.google.gson.annotations.SerializedName


data class LaunchFailureDetails(

    @SerializedName("time") var time: Int? = null,
    @SerializedName("altitude") var altitude: String? = null,
    @SerializedName("reason") var reason: String? = null

)