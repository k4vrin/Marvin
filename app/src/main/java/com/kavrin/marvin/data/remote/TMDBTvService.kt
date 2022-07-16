package com.kavrin.marvin.data.remote

import com.kavrin.marvin.BuildConfig
import com.kavrin.marvin.domain.model.tv.api.TvApiResponse
import com.kavrin.marvin.domain.model.tv.api.TvGenreApiResponse
import com.kavrin.marvin.domain.model.tv.api.detail.SingleTvApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBTvService {

	@GET("tv/popular")
	suspend fun getPopularTvs(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): TvApiResponse


	@GET("tv/top_rated")
	suspend fun getTopRatedTvs(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): TvApiResponse


	@GET("trending/tv/day")
	suspend fun getTrendingTvs(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): TvApiResponse


	@GET("search/tv")
	suspend fun searchTvs(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("query") query: String,
		@Query("page") page: Int = 1,
		@Query("include_adult") includeAdult: Boolean = true
	): TvApiResponse


	@GET("genre/tv/list")
	suspend fun getTvGenres(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY
	): TvGenreApiResponse

	@GET("tv/{id}")
	suspend fun getTvDetails(
		@Path("id") id: Int,
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("append_to_response") append: String = "videos,images,credits,external_ids,recommendations,similar"
	): Response<SingleTvApiResponse>
}