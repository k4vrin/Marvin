package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.movie.api.collection.MovieCollection
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.util.Constants.COLLECTION_BACKDROP_KEY
import com.kavrin.marvin.util.Constants.COLLECTION_NAME_KEY
import com.kavrin.marvin.util.Constants.COLLECTION_OVERVIEW_KEY
import com.kavrin.marvin.util.NetworkResult

class GetCollection(
    private val repository: Repository
) {

    private var data: MovieCollection? = null

    suspend operator fun invoke(id: Int): NetworkResult {
        val response =
            try {
                repository.getMovieCollection(id = id)
            } catch (e: Exception) {
                return NetworkResult.Error(message = e.message)
            }

        return when {
            response.message().toString()
                .contains("timeout") -> NetworkResult.Error(message = "Timeout")
            response.code() == 401 -> NetworkResult.Error(message = "Invalid API key.")
            response.code() == 404 -> NetworkResult.Error(message = "The resources could not be found.")
            response.isSuccessful -> {
                data = response.body()
                data?.parts?.let { repository.saveMovies(movies = it) }
                NetworkResult.Success()
            }
            else -> NetworkResult.Error(message = response.message())
        }
    }

    fun getCollectionNameAndOverview(): Map<String, String?> {
        return mapOf(
            COLLECTION_NAME_KEY to data?.name,
            COLLECTION_OVERVIEW_KEY to data?.overview,
            COLLECTION_BACKDROP_KEY to data?.backdropPath
        )
    }

    fun getCollectionMovies(): List<Movie>? {
        return data?.parts
    }
}