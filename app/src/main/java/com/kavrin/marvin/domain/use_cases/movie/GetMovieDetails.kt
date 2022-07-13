package com.kavrin.marvin.domain.use_cases.movie

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.common.Backdrop
import com.kavrin.marvin.domain.model.common.Cast
import com.kavrin.marvin.domain.model.common.Crew
import com.kavrin.marvin.domain.model.common.Video
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import com.kavrin.marvin.util.NetworkResult
import kotlin.random.Random

class GetMovieDetails(
    private val repository: Repository
) {

    private var data: SingleMovieApiResponse? = null

    suspend operator fun invoke(id: Int): NetworkResult<SingleMovieApiResponse> {
        val response = repository.getMovieDetails(id = id)
        return when {
            response.message().toString()
                .contains("timeout") -> NetworkResult.Error(message = "Timeout")
            response.code() == 401 -> NetworkResult.Error(message = "Invalid API key.")
            response.code() == 404 -> NetworkResult.Error(message = "The resources could not be found.")
            response.isSuccessful -> {
                data = response.body()
                NetworkResult.Success()
            }
            else -> NetworkResult.Error(message = response.message())
        }
    }

    fun getRuntime(): Int? {
        return data?.runtime
    }

    fun getGenre(): List<String>? {
        return data?.genres?.map {
            it.name
        }
    }

    fun getImdbId(): String? {
        return data?.imdbId
    }

    fun getOfficialTrailer(): Video? {
        return data?.videos?.videos?.find {
            it.site == "YouTube" && it.name.lowercase().contains("trailer")
        }
    }

    fun getVideos(): List<Video>? {
        return data?.videos?.videos?.filter {
            it.site == "YouTube"
        }
    }

    fun getTrailerBackdrop(): Backdrop? {
        return data?.images?.backdrops?.size?.let { Random.nextInt(from = 1, until = it) }?.let {
            data?.images?.backdrops?.get(
                it
            )
        }
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
                    {
                        it.job == "Producer"
                    },
                    {
                        it.job == "Story"
                    },
                    {
                        it.job == "Screenplay"
                    },
                    {
                        it.job == "Writer"
                    },
                    {
                        it.job == "Director"
                    },
                )
            )
    }
}