package com.keerthy.spacex.model

import com.google.gson.annotations.SerializedName


data class Fairings(

    @SerializedName("reused") var reused: Boolean? = null,
    @SerializedName("recovery_attempt") var recoveryAttempt: Boolean? = null,
    @SerializedName("recovered") var recovered: Boolean? = null,
    @SerializedName("ship") var ship: String? = null

)