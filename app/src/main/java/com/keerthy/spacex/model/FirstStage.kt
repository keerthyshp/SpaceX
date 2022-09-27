package com.keerthy.spacex.model

import com.google.gson.annotations.SerializedName


data class FirstStage(

    @SerializedName("cores") var cores: ArrayList<Cores> = arrayListOf()

)