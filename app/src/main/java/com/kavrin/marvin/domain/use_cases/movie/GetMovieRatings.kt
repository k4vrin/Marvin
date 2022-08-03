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
        return if (data == null) {
            val response =
                try {
                    repository.getRatings(id = id)
                } catch (e: Exception) {
                    return NetworkResult.Error(message = e.message)
                }
            when {
                response.message().toString()
                    .contains("timeout") -> {
                    data = null
                    NetworkResult.Success()
                }
                response.body()!!.errorMessage.lowercase().contains("maximum usage") -> {
                    data = IMDbRatingApiResponse(
                        errorMessage = "marvin",
                        imDb = null,
                        theMovieDb = null,
                        rottenTomatoes = null,
                        metacritic = null
                    )
                    NetworkResult.Success()
                }
                response.body()!!.errorMessage.lowercase().contains("year") -> {
                    response.body()?.let { res ->
                        data = IMDbRatingApiResponse(
                            errorMessage = "",
                            imDb = res.imDb,
                            theMovieDb = res.theMovieDb,
                            rottenTomatoes = res.rottenTomatoes,
                            metacritic = res.metacritic
                        )
                    }
                    NetworkResult.Success()
                }
                response.isSuccessful -> {
                    data = response.body()
                    NetworkResult.Success()
                }
                else -> {
                    data = IMDbRatingApiResponse(
                        errorMessage = "marvin",
                        imDb = null,
                        theMovieDb = null,
                        rottenTomatoes = null,
                        metacritic = null
                    )
                    NetworkResult.Success()
                }
            }
        } else {
            NetworkResult.Success()
        }
    }

    fun getRatingsValue(): Map<String, String?> {
        return if (data != null && data!!.errorMessage.contains("marvin"))
            emptyMap()
        else
            mapOf(
                IMDB to data?.imDb,
                TMDB to data?.theMovieDb,
                META to data?.metacritic,
                ROTTEN to data?.rottenTomatoes
            )
    }
}