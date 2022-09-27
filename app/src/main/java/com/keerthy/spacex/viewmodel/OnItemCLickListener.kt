package com.keerthy.spacex.viewmodel

import com.keerthy.spacex.model.SpaceXDto

interface OnItemCLickListener {
    fun onItemClick(selected: SpaceXDto)
}