package com.keerthy.spacex.model

import com.google.gson.annotations.SerializedName


data class Payloads(

    @SerializedName("payload_id") var payloadId: String? = null,
    @SerializedName("norad_id") var noradId: ArrayList<String> = arrayListOf(),
    @SerializedName("reused") var reused: Boolean? = null,
    @SerializedName("customers") var customers: ArrayList<String> = arrayListOf(),
    @SerializedName("nationality") var nationality: String? = null,
    @SerializedName("manufacturer") var manufacturer: String? = null,
    @SerializedName("payload_type") var payloadType: String? = null,
    @SerializedName("payload_mass_kg") var payloadMassKg: Float? = null,
    @SerializedName("payload_mass_lbs") var payloadMassLbs: Float? = null,
    @SerializedName("orbit") var orbit: String? = null,
    @SerializedName("orbit_params") var orbitParams: OrbitParams? = OrbitParams()

)