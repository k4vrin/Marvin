package com.kavrin.marvin.domain.repository

import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse

interface IMDbDataSource {

    suspend fun getRatings(id: String): IMDbRatingApiResponse
}