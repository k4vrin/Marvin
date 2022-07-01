package com.kavrin.marvin.data.local.dao.movie.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MoviePopularRemoteKeys

@Dao
interface MoviePopularRemoteKeysDao {

	@Query("SELECT * FROM movie_popular_remote_table WHERE id = :id")
	suspend fun getPopularRemoteKeys(id: Int): MoviePopularRemoteKeys?

	@Query("SELECT * FROM movie_popular_remote_table ORDER BY lastUpdated ASC LIMIT 1")
	suspend fun getPopularLastUpdate(): MoviePopularRemoteKeys?

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun addPopularRemoteKeys(popularRemoteKeys: List<MoviePopularRemoteKeys>)

	@Query("DELETE FROM movie_popular_remote_table")
	suspend fun deleteAllPopularRemoteKeys()
}