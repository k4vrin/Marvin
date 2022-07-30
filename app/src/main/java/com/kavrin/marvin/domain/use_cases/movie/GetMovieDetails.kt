package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.util.NetworkResult
import kotlin.random.Random

class GetMovieDetails(
    private val repository: Repository
) {

    private var data: SingleMovieApiResponse? = null

    suspend operator fun invoke(id: Int): NetworkResult {
        val response =
            try {
                repository.getMovieDetails(id = id)
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
                data?.similar?.movies?.let { repository.saveMovies(it) }
                data?.recommendations?.movies?.let { repository.saveMovies(it) }
                NetworkResult.Success()
            }
            else -> NetworkResult.Error(message = response.message())
        }
    }

    fun getImdbId(): String? {
        return data?.imdbId
    }

    fun getMovieToolbar(): Map<String, String?> {
        return buildMap {
            put(
                key = MovieUseCaseKeys.TITLE,
                value = data?.title
            )
            put(
                key = MovieUseCaseKeys.DIRECTOR,
                value = data?.credits?.crew?.filter {
                    it.job == "Director"
                }?.joinToString {
                    it.name
                }
            )
            put(
                key = MovieUseCaseKeys.BACKDROP,
                value = data?.backdrop
            )
        }
    }

    fun getReleaseRuntimeStatus(): Map<String, String?> {
        return buildMap {
            put(
                key = MovieUseCaseKeys.RELEASE_DATE,
                value = data?.releaseDate
            )

            put(
                key = MovieUseCaseKeys.RUNTIME,
                value = data?.runtime?.toString()
            )

            put(
                key = MovieUseCaseKeys.STATUS,
                value = data?.status
            )
        }
    }

    fun getOverview(): String? {
        return data?.overview
    }

    fun getGenres(): List<String>? {
        return data?.genres?.map {
            it.name
        }
    }

    fun getOfficialTrailer(): Video? {
        return data
            ?.videos
            ?.videos
            ?.find {
                (it.site == "YouTube" && it.name.lowercase().contains("trailer") && it.official)
                        || (it.site == "YouTube" && it.name.lowercase().contains("trailer"))
            }
    }

    fun getVideos(): List<Video>? {
        return data
            ?.videos
            ?.videos
            ?.filter {
                it.site == "YouTube"
            }
    }

    fun getTrailerBackdrop(): Backdrop? {
        return if (!data?.images?.backdrops.isNullOrEmpty()) {
            data
                ?.images
                ?.backdrops
                ?.size
                ?.let { size -> Random.nextInt(until = size) }
                ?.let { index ->
                    data
                        ?.images
                        ?.backdrops
                        ?.get(index)
                }
        } else
            null

    }

    fun getReviews(): List<Review>? {
        return data?.reviews?.reviews
    }

    fun getCollectionId(): Int? {
        return data?.belongsToCollection?.id
    }

    fun getCast(): List<Cast>? {
        return data?.credits?.cast
    }

    fun getCrew(): List<Crew>? {
        return data?.credits?.crew?.filter {
            it.job == "Screenplay" || it.job == "Producer" || it.job == "Director" || it.job == "Story" || it.job == "Writer"
        }
            ?.sortedWith(
                compareBy(
                    { it.job == "Producer" },
                    { it.job == "Story" },
                    { it.job == "Screenplay" },
                    { it.job == "Writer" },
                    { it.job == "Director" },
                )
            )
    }


    fun getRecommendations(): List<Movie>? {
        return data?.recommendations?.movies
    }

    fun getSimilar(): List<Movie>? {
        return data?.similar?.movies
    }
}

object MovieUseCaseKeys {
    const val TITLE = "title"
    const val DIRECTOR = "director"
    const val BACKDROP = "backdrop"
    const val RELEASE_DATE = "release"
    const val RUNTIME = "runtime"
    const val STATUS = "status"
}