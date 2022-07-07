package com.kavrin.marvin.data.local.dao.tv

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.tv.entities.TvTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending

@Dao
interface TvTrendingDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertTrending(tvTrending: List<TvTrending>)

	@Query("DELETE FROM tv_trending_table")
	suspend fun deleteAllTrending()

	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query("SELECT * FROM tv_table, tv_trending_table WHERE tvId = trendingTvId ORDER BY tv_trending_table.id ASC")
	fun getTvAndTrending(): PagingSource<Int, TvAndTrending>

	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query("SELECT * FROM tv_table, tv_trending_table WHERE tvId = trendingTvId ORDER BY popularity DESC LIMIT 5")
	fun getCarouselTvs(): PagingSource<Int, TvAndTrending>

	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query("SELECT * FROM tv_table, tv_trending_table WHERE tvId = trendingTvId ORDER BY tv_trending_table.id ASC LIMIT 8")
	fun getHomeTvAndTrending(): PagingSource<Int, TvAndTrending>
}