package com.kavrin.marvin.util

sealed class NetworkResult<T>(
    val message: String? = null
) {
    class Success<T> : NetworkResult<T>()
    class Error<T>(message: String?) : NetworkResult<T>(message = message)
    class Loading<T> : NetworkResult<T>()
}
