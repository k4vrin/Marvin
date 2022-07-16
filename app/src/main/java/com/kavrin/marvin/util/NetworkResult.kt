package com.kavrin.marvin.util

sealed class NetworkResult(
    val message: String? = null
) {
    class Success : NetworkResult()
    class Error(message: String?) : NetworkResult(message = message)
    class Loading : NetworkResult()
}
