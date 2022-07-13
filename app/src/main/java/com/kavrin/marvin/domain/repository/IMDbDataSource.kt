package com.kavrin.marvin.domain.repository

import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import retrofit2.Response

interface IMDbDataSource {

    suspend fun getRatings(id: String): Response<IMDbRatingApiResponse>
}