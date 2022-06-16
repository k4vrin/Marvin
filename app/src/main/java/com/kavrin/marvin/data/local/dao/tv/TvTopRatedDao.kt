package com.kavrin.marvin.data.local.dao.tv

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.tv.entities.TvTopRated
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTopRated

@Dao
interface TvTopRatedDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTopRated(tvTopRated: TvTopRated)

	@Query("DELETE FROM tv_top_rated_table")
	suspend fun deleteAllTopRated()

	@Transaction
	@Query("SELECT * FROM tv_table WHERE tvId = :tvId")
	fun getTvAndTopRated(tvId: Int): PagingSource<Int, TvAndTopRated>
}