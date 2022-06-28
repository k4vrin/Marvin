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

    @Query("SELECT * FROM movie_genre_table WHERE id = :genreId")
    suspend fun getMovieGenre(genreId: Int): MovieGenre

    @Query("SELECT * FROM tv_genre_table WHERE id = :genreId")
    suspend fun getTvGenre(genreId: Int): MovieGenre
}