package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.api.collection.MovieCollection
import com.kavrin.marvin.util.Resource
import com.kavrin.marvin.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCollection(
    private val repository: Repository
) {

    operator fun invoke(id: Int): Flow<Resource<MovieCollection?>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getMovieCollection(id = id)
            when {
                response.message().toString()
                    .contains("timeout") -> emit(Resource.Error(message = UiText.DynamicString("Timeout")))
                response.code() == 401 -> emit(Resource.Error(message = UiText.DynamicString("Invalid API key.")))
                response.code() == 404 -> emit(Resource.Error(message = UiText.DynamicString("The resources could not be found.")))
                response.isSuccessful -> {
                    emit(Resource.Success(getCollection(response.body())))
                }
                else -> emit(Resource.Error(message = UiText.DynamicString(response.message())))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = UiText.DynamicString(value = e.message.toString())))
        }
    }

    private fun getCollection(data: MovieCollection?): MovieCollection? {
        return MovieCollection(
            backdropPath = data?.backdropPath,
            id = data?.id,
            name = data?.name,
            overview = data?.overview,
            posterPath = data?.posterPath,
            parts = data?.parts?.sortedBy { it.releaseDate }
        )
    }
}