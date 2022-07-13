package com.kavrin.marvin.data.remote

import com.kavrin.marvin.BuildConfig
import com.kavrin.marvin.domain.model.movie.api.MovieApiResponse
import com.kavrin.marvin.domain.model.movie.api.MovieGenreApiResponse
import com.kavrin.marvin.domain.model.movie.api.detail.SingleMovieApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBMovieService {

	@GET("movie/popular")
	suspend fun getPopularMovies(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): MovieApiResponse


	@GET("movie/top_rated")
	suspend fun getTopRatedMovies(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): MovieApiResponse


	@GET("trending/movie/day")
	suspend fun getTrendingMovies(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): MovieApiResponse


	@GET("search/movie")
	suspend fun searchMovies(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("query") query: String,
		@Query("page") page: Int = 1,
		@Query("include_adult") includeAdult: Boolean = true
	): MovieApiResponse


	@GET("genre/movie/list")
	suspend fun getMovieGenres(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY
	): MovieGenreApiResponse

	@GET("movie/{id}")
	suspend fun getMovieDetails(
		@Path("id") id: Int,
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("append_to_response") append: String = "videos,images,credits"
	): Response<SingleMovieApiResponse>
}