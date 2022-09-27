package com.keerthy.spacex.network

sealed class Resource {
    object Loading : Resource()
    class Failure(val e: Throwable) : Resource()
    class Success(val data: Any) : Resource()
    object Empty : Resource()
}