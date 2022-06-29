package com.kavrin.marvin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kavrin.marvin.domain.model.movie.entities.MovieGenre
import com.kavrin.marvin.domain.model.tv.entities.TvGenre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieGenres(genres: List<MovieGenre>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTvGenres(genres: List<TvGenre>)

    @Query("SELECT name FROM movie_genre_table WHERE id = :id")
    suspend fun getMovieGenre(id: Int): String

    @Query("SELECT name FROM tv_genre_table WHERE id = :id")
    suspend fun getTvGenre(id: Int): String
}