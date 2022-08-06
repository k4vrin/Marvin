package com.kavrin.marvin.domain.repository

import androidx.paging.PagingData
import com.kavrin.marvin.domain.model.tv.api.detail.SingleTvApiResponse
import com.kavrin.marvin.domain.model.tv.api.season.SeasonApiResponse
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TvRemoteDataSource {

	fun getPopularTvs(): Flow<PagingData<TvAndPopular>>
	fun getTopRatedTvs(): Flow<PagingData<TvAndTopRated>>
	fun getTrendingTvs(): Flow<PagingData<TvAndTrending>>
	fun getCarouselTvs(): Flow<PagingData<TvAndTrending>>
	fun getHomePopularTvs(): Flow<PagingData<TvAndPopular>>
	fun getHomeTopRatedTvs(): Flow<PagingData<TvAndTopRated>>
	fun getHomeTrendingTvs(): Flow<PagingData<TvAndTrending>>
	fun searchTvs(query: String): Flow<PagingData<Tv>>
	suspend fun saveTvGenres()
	suspend fun getTvDetails(id: Int): Response<SingleTvApiResponse>
	suspend fun getTvSeason(id: Int, seasonNumber: Int): Response<SeasonApiResponse>
}