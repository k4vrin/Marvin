package com.kavrin.marvin.data.local.dao.tv

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.tv.entities.TvTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending

@Dao
interface TvTrendingDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTrending(tvTrending: TvTrending)

	@Query("DELETE FROM tv_top_rated_table")
	suspend fun deleteAllTrending()

	@Transaction
	@Query("SELECT * FROM tv_table")
	fun getTvAndTrending(): PagingSource<Int, TvAndTrending>
}