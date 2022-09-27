package com.keerthy.spacex.model

import com.google.gson.annotations.SerializedName


data class Telemetry(

    @SerializedName("flight_club") var flightClub: String? = null

)