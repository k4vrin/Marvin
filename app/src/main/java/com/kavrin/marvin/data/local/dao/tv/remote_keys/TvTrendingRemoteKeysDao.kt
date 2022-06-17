package com.kavrin.marvin.data.local.dao.tv.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kavrin.marvin.domain.model.tv.entities.remote_keys.TvTrendingRemoteKeys

@Dao
interface TvTrendingRemoteKeysDao {

	@Query("SELECT * FROM tv_trending_remote_table WHERE tvTrendingId = :id")
	suspend fun getTrendingRemoteKeys(id: Int): TvTrendingRemoteKeys

	@Insert
	suspend fun addTrendingRemoteKeys(trendingRemoteKeys: List<TvTrendingRemoteKeys>)

	@Query("DELETE FROM tv_trending_remote_table")
	suspend fun deleteAllTrendingRemoteKeys()
}