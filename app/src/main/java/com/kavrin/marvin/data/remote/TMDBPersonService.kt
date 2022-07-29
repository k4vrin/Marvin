package com.kavrin.marvin.data.remote

import com.kavrin.marvin.BuildConfig
import com.kavrin.marvin.domain.model.person.PersonApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBPersonService {


    @GET("person/{id}")
    suspend fun getPersonDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("append_to_response") append: String = "movie_credits,tv_credits"
    ): Response<PersonApiResponse>


}