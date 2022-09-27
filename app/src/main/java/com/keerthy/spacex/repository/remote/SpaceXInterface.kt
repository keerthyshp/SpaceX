package com.keerthy.spacex.repository.remote

import com.keerthy.spacex.model.SpaceXDto
import com.keerthy.spacex.network.ApiConstants
import retrofit2.http.GET

interface SpaceXInterface {

    @GET(ApiConstants.LAUNCHES)
    suspend fun getDataList(): List<SpaceXDto>

}