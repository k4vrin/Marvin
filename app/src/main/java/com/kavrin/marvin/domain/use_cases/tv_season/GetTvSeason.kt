package com.kavrin.marvin.domain.use_cases.tv_season

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.common.Cast
import com.kavrin.marvin.domain.model.common.Crew
import com.kavrin.marvin.domain.model.tv.api.season.Episode
import com.kavrin.marvin.domain.model.tv.api.season.SeasonApiResponse
import com.kavrin.marvin.util.NetworkResult

class GetTvSeason(
    private val repository: Repository
) {
    private var data: SeasonApiResponse? = null
    private var episode: Episode? = null

    suspend operator fun invoke(id: Int, seasonNumber: Int): NetworkResult {
        return if (data == null) {
            val response =
                try {
                    repository.getTvSeason(id, seasonNumber)
                } catch (e: Exception) {
                    return NetworkResult.Error(e.message)
                }
            when {
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
        } else {
            NetworkResult.Success()
        }
    }

    fun getSeasonInfo(): Map<String, String?> {
        return buildMap {
            put(
                key = POSTER,
                value = data?.posterPath
            )
            put(
                key = NAME,
                value = data?.name
            )
            put(
                key = AIR_DATE,
                value = data?.airDate
            )
            put(
                key = OVERVIEW,
                value = data?.overview
            )
        }
    }

    fun getEpisodes(): List<EpisodeSummary> {
        return buildList {
            data?.episodes?.forEach { episode ->
                add(
                    EpisodeSummary(
                        id = episode.id,
                        overview = episode.overview,
                        stillPath = episode.stillPath,
                        name = episode.name,
                        voteAverage = episode.voteAverage,
                        voteCount = episode.voteCount,
                        episodeNumber = episode.episodeNumber
                    )
                )
            }
        }
    }

    fun getEpisode(id: Int) {
        episode = data?.episodes?.find { it.id == id }
    }

    fun getEpisodeToolbar(): Map<String, String?> {
        return buildMap {
            put(
                key = EPISODE_POSTER,
                value = episode?.stillPath
            )
            put(
                key = EPISODE_NAME,
                value = episode?.name
            )
            put(
                key = EPISODE_DIRECTOR,
                value = episode?.crew?.filter { it.job == "Director" }?.joinToString { it.name }
            )
        }
    }

    fun getEpisodeInfo(): Map<String, String?> {
        return buildMap {
            put(
                key = EPISODE_AIR_DATE,
                value = episode?.airDate
            )
            put(
                key = EPISODE_RUNTIME,
                value = episode?.runtime?.toString()
            )
            put(
                key = EPISODE_OVERVIEW,
                value = episode?.overview
            )

        }
    }

    fun getEpisodeCast(): List<Cast> {
        return buildList {
            episode?.guestStars?.let { addAll(it) }
        }
    }

    fun getEpisodeCrew(): List<Crew> {
        return buildList {
            episode?.crew?.let { addAll(it) }
        }
    }

    companion object {
        const val POSTER = "poster"
        const val NAME = "name"
        const val AIR_DATE = "air_date"
        const val OVERVIEW = "overview"
        const val EPISODE_DIRECTOR = "episode_director"
        const val EPISODE_POSTER = "episode_poster"
        const val EPISODE_NAME = "episode_name"
        const val EPISODE_AIR_DATE = "episode_air_date"
        const val EPISODE_RUNTIME = "episode_runtime"
        const val EPISODE_OVERVIEW = "episode_overview"
    }

}

data class EpisodeSummary(
    val id: Int,
    val overview: String,
    val stillPath: String,
    val name: String,
    val voteAverage: Double,
    val voteCount: Int,
    val episodeNumber: Int
)