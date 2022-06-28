package com.kavrin.marvin.domain.repository

import androidx.paging.PagingData
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import kotlinx.coroutines.flow.Flow

interface TvRemoteDataSource {

	fun getPopularTvs(): Flow<PagingData<TvAndPopular>>
	fun getTopRatedTvs(): Flow<PagingData<TvAndTopRated>>
	fun getTrendingTvs(): Flow<PagingData<TvAndTrending>>
	fun getCarouselTvs(): Flow<PagingData<TvAndTrending>>
	fun getHomePopularTvs(): Flow<PagingData<TvAndPopular>>
	fun getHomeTopRatedTvs(): Flow<PagingData<TvAndTopRated>>
	fun getHomeTrendingTvs(): Flow<PagingData<TvAndTrending>>
	fun searchTvs(): Flow<PagingData<Tv>>
	suspend fun saveTvGenres()
}