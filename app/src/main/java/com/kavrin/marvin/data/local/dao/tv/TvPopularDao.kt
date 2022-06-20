package com.kavrin.marvin.data.local.dao.tv

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTopRated
import com.kavrin.marvin.domain.model.tv.entities.TvPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular

@Dao
interface TvPopularDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertPopular(tvPopular: TvPopular)

	@Query("DELETE FROM tv_popular_table")
	suspend fun deleteAllPopular()

	@Transaction
	@Query("SELECT * FROM tv_table")
	fun getTvAndPopular(): PagingSource<Int, TvAndPopular>

	@Transaction
	@Query("SELECT * FROM tv_table LIMIT 8")
	fun getHomeTvAndPopular(): PagingSource<Int, TvAndPopular>
}