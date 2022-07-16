package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.util.Constants.IMDB
import com.kavrin.marvin.util.Constants.META
import com.kavrin.marvin.util.Constants.ROTTEN
import com.kavrin.marvin.util.Constants.TMDB
import com.kavrin.marvin.util.NetworkResult

class GetMovieRatings(
    private val repository: Repository
) {

    private var data: IMDbRatingApiResponse? = null

    suspend operator fun invoke(id: String): NetworkResult {
        val response =
            try {
                repository.getRatings(id = id)
            } catch (e: Exception) {
                return NetworkResult.Error(message = e.message)
            }
        return when {
            response.message().toString()
                .contains("timeout") -> NetworkResult.Error(message = "Timeout")
            response.body()!!.errorMessage.isNotBlank() -> NetworkResult.Error(message = response.body()!!.errorMessage)
            response.isSuccessful -> {
                data = response.body()
                NetworkResult.Success()
            }
            else -> NetworkResult.Error(message = response.message())
        }
    }

    fun getRatingsValue(): Map<String, String?> {
        return mapOf(
            IMDB to data?.imDb,
            TMDB to data?.theMovieDb,
            META to data?.metacritic,
            ROTTEN to data?.rottenTomatoes
        )
    }
}