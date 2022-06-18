package com.kavrin.marvin.domain.repository

import androidx.paging.PagingData
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

	fun getPopularMovies(): Flow<PagingData<MovieAndPopular>>
	fun getTopRatedMovies(): Flow<PagingData<MovieAndTopRated>>
	fun getTrendingMovies(): Flow<PagingData<MovieAndTrending>>
	fun searchMovies(): Flow<PagingData<Movie>>

	fun getPopularTvs(): Flow<PagingData<TvAndPopular>>
	fun getTopRatedTvs(): Flow<PagingData<TvAndTopRated>>
	fun getTrendingTvs(): Flow<PagingData<TvAndTrending>>
	fun searchTvs(): Flow<PagingData<Tv>>


}