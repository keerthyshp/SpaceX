package com.keerthy.spacex.model

import com.google.gson.annotations.SerializedName


data class Rocket(

    @SerializedName("rocket_id") var rocketId: String? = null,
    @SerializedName("rocket_name") var rocketName: String? = null,
    @SerializedName("rocket_type") var rocketType: String? = null,
    @SerializedName("first_stage") var firstStage: FirstStage? = FirstStage(),
    @SerializedName("second_stage") var secondStage: SecondStage? = SecondStage(),
    @SerializedName("fairings") var fairings: Fairings? = Fairings()

)