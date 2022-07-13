package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.util.NetworkResult

class GetMovieDetails(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int): NetworkResult<SingleMovieApiResponse> {
        val response = repository.getMovieDetails(id = id)
        return when {
            response.message().toString()
                .contains("timeout") -> NetworkResult.Error(message = "Timeout")
            response.code() == 401 -> NetworkResult.Error(message = "Invalid API key.")
            response.code() == 404 -> NetworkResult.Error(message = "The resources could not be found.")
            response.isSuccessful -> NetworkResult.Success(data = response.body()!!)
            else -> NetworkResult.Error(message = response.message())
        }
    }
}