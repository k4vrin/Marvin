package com.kavrin.marvin.data.local.dao.tv

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.tv.entities.TvTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated

@Dao
interface TvTopRatedDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertTopRated(tvTopRated: List<TvTopRated>)

	@Query("DELETE FROM tv_top_rated_table")
	suspend fun deleteAllTopRated()

	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query("SELECT * FROM tv_table, tv_top_rated_table WHERE tvId = topRatedTvId ORDER BY tv_top_rated_table.id ASC")
	fun getTvAndTopRated(): PagingSource<Int, TvAndTopRated>

	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query("SELECT * FROM tv_table, tv_top_rated_table WHERE tvId = topRatedTvId ORDER BY tv_top_rated_table.id ASC LIMIT 8")
	fun getHomeTvAndTopRated(): PagingSource<Int, TvAndTopRated>
}