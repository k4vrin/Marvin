package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.util.DispatchersProvider
import com.kavrin.marvin.util.Resource
import com.kavrin.marvin.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

class GetMovieDetails(
    private val repository: Repository,
    private val dispatchers: DispatchersProvider
) {

    operator fun invoke(id: Int): Flow<Resource<UiMovie>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getMovieDetails(id = id)

            when {
                response.message().toString()
                    .contains("timeout") -> emit(Resource.Error(message = UiText.DynamicString("Timeout")))
                response.code() == 401 -> emit(Resource.Error(message = UiText.DynamicString("Invalid API key.")))
                response.code() == 404 -> emit(Resource.Error(message = UiText.DynamicString("The resources could not be found.")))
                response.isSuccessful -> {
                    val uiMovie = getUiMovie(response.body())
                    emit(Resource.Success(data = uiMovie))
                }
                else -> emit(Resource.Error(message = UiText.DynamicString(response.message())))
            }

        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?.let { UiText.DynamicString(it) }))
        }
    }.flowOn(dispatchers.io)

    private fun getUiMovie(apiMovie: SingleMovieApiResponse?): UiMovie {
        return UiMovie(
            imdbId = getImdbId(apiMovie),
            toolbarInfo = getMovieToolbar(apiMovie),
            releaseRuntimeStatus = getReleaseRuntimeStatus(apiMovie),
            overview = getOverview(apiMovie),
            genres = getGenres(apiMovie),
            trailer = getOfficialTrailer(apiMovie),
            trailerBackdrop = getTrailerBackdrop(apiMovie),
            videos = getVideos(apiMovie),
            cast = getCast(apiMovie),
            crew = getCrew(apiMovie),
            collectionId = getCollectionId(apiMovie),
            reviews = getReviews(apiMovie),
            recommendations = getRecommendations(apiMovie),
            similars = getSimilar(apiMovie)
        )
    }

    private fun getImdbId(data: SingleMovieApiResponse?): String? {
        return data?.imdbId
    }

    private fun getMovieToolbar(data: SingleMovieApiResponse?): Map<String, String?> {
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

    private fun getReleaseRuntimeStatus(data: SingleMovieApiResponse?): Map<String, String?> {
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

    private fun getOverview(data: SingleMovieApiResponse?): String? {
        return data?.overview
    }

    private fun getGenres(data: SingleMovieApiResponse?): List<String>? {
        return data?.genres?.map {
            it.name
        }
    }

    private fun getOfficialTrailer(data: SingleMovieApiResponse?): Video? {
        return data
            ?.videos
            ?.videos
            ?.find {
                (it.site == "YouTube" && it.name.lowercase().contains("trailer") && it.official)
                        || (it.site == "YouTube" && it.name.lowercase().contains("trailer"))
            }
    }

    private fun getVideos(data: SingleMovieApiResponse?): List<Video>? {
        return data
            ?.videos
            ?.videos
            ?.filter {
                it.site == "YouTube"
            }
    }

    private fun getTrailerBackdrop(data: SingleMovieApiResponse?): Backdrop? {
        return if (!data?.images?.backdrops.isNullOrEmpty()) {
            data
                ?.images
                ?.backdrops
                ?.size
                ?.let { size -> Random.nextInt(until = size) }
                ?.let { index ->
                    data
                        .images
                        .backdrops[index]
                }
        } else
            null

    }

    private fun getReviews(data: SingleMovieApiResponse?): List<Review>? {
        return data?.reviews?.reviews
    }

    private fun getCollectionId(data: SingleMovieApiResponse?): Int? {
        return data?.belongsToCollection?.id
    }

    private fun getCast(data: SingleMovieApiResponse?): List<Cast>? {
        return data?.credits?.cast
    }

    private fun getCrew(data: SingleMovieApiResponse?): List<Crew>? {
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


    private fun getRecommendations(data: SingleMovieApiResponse?): List<Movie>? {
        return data
            ?.recommendations
            ?.movies
            ?.filter { it.title.isNotBlank() }
    }

    private fun getSimilar(data: SingleMovieApiResponse?): List<Movie>? {
        return data
            ?.similar
            ?.movies
            ?.filter { it.title.isNotBlank() }
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

