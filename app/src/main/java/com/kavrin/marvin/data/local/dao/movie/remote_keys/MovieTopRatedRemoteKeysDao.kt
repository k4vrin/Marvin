package com.kavrin.marvin.data.local.dao.movie.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MovieTopRatedRemoteKeys

@Dao
interface MovieTopRatedRemoteKeysDao {

	@Query("SELECT * FROM movie_top_rated_remote_table WHERE movieTopRatedId = :id")
	suspend fun getTopRatedRemoteKeys(id: Int): MovieTopRatedRemoteKeys

	@Insert
	suspend fun addTopRatedRemoteKeys(topRatedRemoteKeys: List<MovieTopRatedRemoteKeys>)

	@Query("DELETE FROM movie_top_rated_remote_table")
	suspend fun deleteAllTopRatedRemoteKeys()
}