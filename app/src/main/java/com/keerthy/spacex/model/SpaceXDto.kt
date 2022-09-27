package com.keerthy.spacex.model

import com.google.gson.annotations.SerializedName


data class SpaceXDto(

    @SerializedName("flight_number") var flightNumber: Int? = null,
    @SerializedName("mission_name") var missionName: String? = null,
    @SerializedName("mission_id") var missionId: ArrayList<String> = arrayListOf(),
    @SerializedName("upcoming") var upcoming: Boolean? = null,
    @SerializedName("launch_year") var launchYear: String? = null,
    @SerializedName("launch_date_unix") var launchDateUnix: Int? = null,
    @SerializedName("launch_date_utc") var launchDateUtc: String? = null,
    @SerializedName("launch_date_local") var launchDateLocal: String? = null,
    @SerializedName("is_tentative") var isTentative: Boolean? = null,
    @SerializedName("tentative_max_precision") var tentativeMaxPrecision: String? = null,
    @SerializedName("tbd") var tbd: Boolean? = null,
    @SerializedName("launch_window") var launchWindow: Int? = null,
    @SerializedName("rocket") var rocket: Rocket? = Rocket(),
    @SerializedName("ships") var ships: ArrayList<String> = arrayListOf(),
    @SerializedName("telemetry") var telemetry: Telemetry? = Telemetry(),
    @SerializedName("launch_site") var launchSite: LaunchSite? = LaunchSite(),
    @SerializedName("launch_success") var launchSuccess: Boolean? = null,
    @SerializedName("launch_failure_details") var launchFailureDetails: LaunchFailureDetails? = LaunchFailureDetails(),
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("details") var details: String? = null,
    @SerializedName("static_fire_date_utc") var staticFireDateUtc: String? = null,
    @SerializedName("static_fire_date_unix") var staticFireDateUnix: Int? = null,
    @SerializedName("timeline") var timeline: Timeline? = Timeline(),
    @SerializedName("crew") var crew: ArrayList<String> = arrayListOf()
)