package com.kavrin.marvin.data.remote

import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface IMDbService {

    @GET
    suspend fun getRating(
        @Url url: String
    ): IMDbRatingApiResponse
}