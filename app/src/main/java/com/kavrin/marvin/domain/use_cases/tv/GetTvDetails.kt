package com.kavrin.marvin.domain.use_cases.tv

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.tv.api.detail.EpisodeToAir
import com.kavrin.marvin.domain.model.tv.api.detail.Season
import com.kavrin.marvin.domain.model.tv.api.detail.SingleTvApiResponse
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.util.Constants.LAST_EPISODE_KEY
import com.kavrin.marvin.util.Constants.NEXT_EPISODE_KEY
import com.kavrin.marvin.util.Constants.TV_DATE_KEY
import com.kavrin.marvin.util.Constants.TV_RUNTIME_KEY
import com.kavrin.marvin.util.Constants.TV_STATUS_KEY
import com.kavrin.marvin.util.Constants.TV_TOTAL_EPISODE_KEY
import com.kavrin.marvin.util.NetworkResult
import kotlin.random.Random

class GetTvDetails(
    private val repository: Repository
) {

    private var data: SingleTvApiResponse? = null

    suspend operator fun invoke(id: Int): NetworkResult {
        val response =
            try {
                repository.getTvDetails(id = id)
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
                data?.similar?.tvs?.let { repository.saveTvs(tvs = it) }
                data?.recommendations?.tvs?.let { repository.saveTvs(tvs = it) }
                NetworkResult.Success()
            }
            else -> NetworkResult.Error(message = response.message())
        }
    }

    fun getImdbId(): String? {
        return data?.externalIds?.imdbId
    }

    fun getRuntimeStatusDateTotal(): Map<String, String?> {

        return mapOf(
            TV_RUNTIME_KEY to data?.episodeRunTime?.let {
                if (it.isNotEmpty()) it.first().toString() else null
            },
            TV_STATUS_KEY to data?.status?.split(" ")?.first(),
            TV_DATE_KEY to data?.firstAirDate,
            TV_TOTAL_EPISODE_KEY to data?.numberOfEpisodes?.toString()
        )
    }

    fun getGenres(): List<String>? {
        return data?.genres?.map { it.name }
    }

    fun getCast(): List<Cast>? {
        return data?.credits?.cast
    }

    fun getCrew(): List<Crew> {

        return buildList {
            data?.createdBy?.forEach { creator ->
                add(
                    Crew(
                        creditId = creator.creditId,
                        id = creator.id,
                        name = creator.name,
                        job = "Creator",
                        profilePath = creator.profilePath,
                        gender = creator.gender,
                        department = "",
                        popularity = 0.0
                    )
                )
            }
            data?.credits?.crew?.filter {
                it.job == "Producer" || it.job == "Executive Producer"
            }
                ?.sortedWith(
                    compareBy(
                        { it.job == "Producer" },
                        { it.job == "Executive Producer" },
                    )
                )?.let {
                    addAll(it)
                }
        }
    }

    fun getReviews(): List<Review>? {
        return data?.reviews?.reviews
    }

    fun getOfficialTrailer(): Video? {
        return data?.videos?.videos?.find {
            (it.site == "YouTube" && it.name.lowercase().contains("trailer") && it.official)
                    || (it.site == "YouTube" && it.name.lowercase().contains("trailer"))
        }
    }

    fun getVideos(): List<Video>? {
        return data?.videos?.videos?.filter {
            it.site == "YouTube"
        }
    }

    fun getTrailerBackdrop(): Backdrop? {
        return if (!data?.images?.backdrops.isNullOrEmpty()) {
            data?.images?.backdrops?.size?.let { Random.nextInt(until = it) }?.let {
                data?.images?.backdrops?.get(it)
            }
        } else
            null
    }

    fun getSeasons(): List<Season>? {
        return data?.seasons?.sortedBy { it.seasonNumber }
    }

    fun getEpisodesToAir(): Map<String, EpisodeToAir?> {
        return buildMap {
            put(key = LAST_EPISODE_KEY, value = data?.lastEpisodeToAir)
            put(key = NEXT_EPISODE_KEY, value = data?.nextEpisodeToAir)
        }
    }

    fun getSimilarTvs(): List<Tv>? {
        return data?.similar?.tvs
    }

    fun getRecommendedTvs(): List<Tv>? {
        return data?.recommendations?.tvs
    }


}