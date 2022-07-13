package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.util.NetworkResult

class GetRatings(
    private val repository: Repository
) {

    suspend operator fun invoke(id: String): NetworkResult<IMDbRatingApiResponse> {
        val response = repository.getRatings(id = id)
        return when {
            response.message().toString()
                .contains("timeout") -> NetworkResult.Error(message = "Timeout")
            response.body()!!.errorMessage.isNotBlank() -> NetworkResult.Error(message = response.body()!!.errorMessage)
            response.isSuccessful -> NetworkResult.Success(data = response.body()!!)
            else -> NetworkResult.Error(message = response.message())
        }
    }
}