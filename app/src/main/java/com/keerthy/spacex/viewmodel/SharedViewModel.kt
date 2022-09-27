package com.keerthy.spacex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keerthy.spacex.model.SpaceXDto
import com.keerthy.spacex.network.Resource
import com.keerthy.spacex.repository.SpaceXRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class SharedViewModel : ViewModel() {

    private val _missionListRequest: MutableStateFlow<Resource> = MutableStateFlow(Resource.Empty)
    val missionListRequest: StateFlow<Resource> = _missionListRequest

    private val _missionList: MutableStateFlow<List<SpaceXDto>> = MutableStateFlow(arrayListOf())
    val missionList: StateFlow<List<SpaceXDto>> = _missionList

    private val _selectedMission: MutableStateFlow<SpaceXDto?> = MutableStateFlow(null)
    val selectedMission: StateFlow<SpaceXDto?> = _selectedMission

    private var repository: SpaceXRepository = SpaceXRepository()

    fun getDataList() = viewModelScope.launch {
        _missionListRequest.value = Resource.Loading
        repository.getDataList().catch { e ->
                _missionListRequest.value = Resource.Failure(e)
            }.collect { data ->
                data.sortedByDescending { it.launchDateUnix }.apply {
                        _missionListRequest.value = Resource.Success(this)
                        _missionList.value = this
                    }

            }
    }

    fun setSelectedMission(selected: SpaceXDto) {
        _selectedMission.value = selected
    }
}