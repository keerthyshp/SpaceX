package com.keerthy.spacex.repository

import com.keerthy.spacex.model.SpaceXDto
import com.keerthy.spacex.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SpaceXRepository {

    fun getDataList(): Flow<List<SpaceXDto>> = flow {
        val r = RetrofitClient.retrofit.getDataList()
        emit(r)
    }.flowOn(Dispatchers.IO)

}