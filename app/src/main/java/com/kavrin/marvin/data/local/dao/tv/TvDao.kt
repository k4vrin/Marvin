package com.kavrin.marvin.data.local.dao.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kavrin.marvin.domain.model.tv.entities.Tv

@Dao
interface TvDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertTv(tv: List<Tv>)

	@Query("SELECT * FROM tv_table WHERE tvId = :tvId")
	suspend fun getSelectedTv(tvId: Int): Tv

	@Query("DELETE FROM tv_table")
	suspend fun deleteAllTvs()
}