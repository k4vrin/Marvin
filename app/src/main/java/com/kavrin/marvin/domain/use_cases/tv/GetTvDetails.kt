package com.kavrin.marvin.domain.use_cases.tv

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.common.*
import com.kavrin.marvin.domain.model.tv.api.detail.EpisodeToAir
import com.kavrin.marvin.domain.model.tv.api.detail.Season
import com.kavrin.marvin.domain.model.tv.api.detail.SingleTvApiResponse
import com.kavrin.marvin.domain.model.tv.entities.Tv
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

    fun getToolbarInfo(): Map<String, String?> {
        return buildMap {
            put(
                key = TvUseCaseKeys.TITLE,
                value = data?.name
            )
            put(
                key = TvUseCaseKeys.BACKDROP,
                value = data?.backdrop
            )
            put(
                key = TvUseCaseKeys.CREATORS,
                value = data
                    ?.createdBy
                    ?.joinToString { it.name }
            )
        }
    }

    fun getRuntimeStatusDateTotal(): Map<String, String?> {

        return buildMap {
            put(
                key = TvUseCaseKeys.RUNTIME,
                value = data
                    ?.episodeRunTime
                    ?.let {
                        if (it.isNotEmpty()) it.first().toString() else null
                    }
            )

            put(
                key = TvUseCaseKeys.STATUS,
                value = data?.status?.split(" ")?.first()
            )

            put(
                key = TvUseCaseKeys.DATE,
                value = data?.firstAirDate
            )
        }
    }

    fun getOverviewTotalEpisodes(): Map<String, String?> {
        return buildMap {
            put(
                key = TvUseCaseKeys.TOTAL_EPISODES,
                value = data?.numberOfEpisodes?.toString()
            )
            put(
                key = TvUseCaseKeys.OVERVIEW,
                value = data?.overview
            )
        }
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
                it.job == "Producer" || it.job == "Story" || it.job == "Writer" || it.job == "Screenplay"
            }
                ?.sortedWith(
                    compareBy(
                        { it.job == "Producer" },
                        { it.job == "Writer" },
                        { it.job == "Story" },
                        { it.job == "Screenplay" },
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
            put(key = TvUseCaseKeys.LAST_EPISODES, value = data?.lastEpisodeToAir)
            put(key = TvUseCaseKeys.NEXT_EPISODES, value = data?.nextEpisodeToAir)
        }
    }

    fun getSimilarTvs(): List<Tv>? {
        return data?.similar?.tvs
    }

    fun getRecommendedTvs(): List<Tv>? {
        return data?.recommendations?.tvs
    }


}

object TvUseCaseKeys {
    const val TITLE = "title"
    const val BACKDROP = "backdrop"
    const val CREATORS = "creators"
    const val RUNTIME = "runtime"
    const val STATUS = "status"
    const val DATE = "date"
    const val OVERVIEW = "overview"
    const val TOTAL_EPISODES = "total_episode"
    const val LAST_EPISODES = "last_episode"
    const val NEXT_EPISODES = "next_episode"
}