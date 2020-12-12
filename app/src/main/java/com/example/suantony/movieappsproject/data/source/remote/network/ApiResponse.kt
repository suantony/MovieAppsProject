package com.example.suantony.movieappsproject.data.source.remote.network

sealed class ApiResponse() {
    object Success : ApiResponse()
    data class Error(val msg: String?) : ApiResponse()
    object Empty : ApiResponse()
    object Loading : ApiResponse()
}

sealed class ApiResponseState<out R> {
    data class Success<out T>(val data: T) : ApiResponseState<T>()
    data class Error(val msg: String?) : ApiResponseState<Nothing>()
    object Empty : ApiResponseState<Nothing>()
    object Loading : ApiResponseState<Nothing>()
}