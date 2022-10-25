package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.util.Constants.IMDB
import com.kavrin.marvin.util.Constants.META
import com.kavrin.marvin.util.Constants.ROTTEN
import com.kavrin.marvin.util.Constants.TMDB
import com.kavrin.marvin.util.Resource
import com.kavrin.marvin.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieRatings(
    private val repository: Repository
) {
    operator fun invoke(id: String): Flow<Resource<Map<String, String?>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getRatings(id = id)

            when {
                response.message().toString()
                    .contains("timeout") -> emit(Resource.Error(message = UiText.DynamicString("Timeout")))
                response.body()?.errorMessage?.lowercase()
                    ?.contains("maximum usage") == true -> emit(
                    Resource.Error(message = UiText.DynamicString("maximum usage"))
                )
                response.body()?.errorMessage?.lowercase()?.contains("year") == true -> {
                    emit(Resource.Success(data = getRatingsValue(response.body())))
                }
                response.isSuccessful -> {
                    emit(Resource.Success(getRatingsValue(response.body())))
                }
                else -> {
                    emit(Resource.Error(message = UiText.DynamicString("${response.body()?.errorMessage}")))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = UiText.DynamicString(value = e.message.toString())))
        }
    }

    private fun getRatingsValue(data: IMDbRatingApiResponse?): Map<String, String?> {
        return mapOf(
            IMDB to data?.imDb,
            TMDB to data?.theMovieDb,
            META to data?.metacritic,
            ROTTEN to data?.rottenTomatoes
        )
    }
}