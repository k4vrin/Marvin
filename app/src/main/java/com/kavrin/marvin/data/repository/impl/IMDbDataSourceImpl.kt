package com.kavrin.marvin.data.repository.impl

import com.kavrin.marvin.BuildConfig
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.remote.IMDbService
import com.kavrin.marvin.domain.model.imdb.IMDbRatingApiResponse
import com.kavrin.marvin.domain.repository.IMDbDataSource
import com.kavrin.marvin.util.Constants.IMDb_BASE_URL
import retrofit2.Response

class IMDbDataSourceImpl(
    private val imdbService: IMDbService,
    private val marvinDatabase: MarvinDatabase
) : IMDbDataSource {


    override suspend fun getRatings(id: String): Response<IMDbRatingApiResponse> {
        return imdbService.getRating(url = IMDb_BASE_URL + "Ratings/${BuildConfig.IMDb_API_KEY}/$id")
    }


}