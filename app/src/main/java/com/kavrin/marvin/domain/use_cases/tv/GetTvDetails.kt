package com.kavrin.marvin.domain.use_cases.tv

import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.domain.model.tv.api.detail.SingleTvApiResponse
import com.kavrin.marvin.util.Constants.TV_DATE_KEY
import com.kavrin.marvin.util.Constants.TV_RUNTIME_KEY
import com.kavrin.marvin.util.Constants.TV_STATUS_KEY
import com.kavrin.marvin.util.NetworkResult

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
                NetworkResult.Success()
            }
            else -> NetworkResult.Error(message = response.message())
        }
    }

    fun getImdbId(): String? {
        return data?.externalIds?.imdbId
    }

    fun getRuntimeStatusDate(): Map<String, String?> {
        return mapOf(
            TV_RUNTIME_KEY to data?.episodeRunTime?.let {
                if (it.isNotEmpty()) it.first().toString() else null
            },
            TV_STATUS_KEY to data?.status?.split(" ")?.first(),
            TV_DATE_KEY to data?.firstAirDate
        )
    }

    fun getGenres(): List<String>? {
        return data?.genres?.map { it.name }
    }


}