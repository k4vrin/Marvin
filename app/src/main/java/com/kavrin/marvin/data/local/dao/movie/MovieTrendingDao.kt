package com.kavrin.marvin.data.local.dao.movie

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.movie.entities.MovieTrending
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending

@Dao
interface MovieTrendingDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertTrending(movieTrending: List<MovieTrending>)

	@Query("DELETE FROM movie_trending_table")
	suspend fun deleteAllTrending()

	@Transaction
	@Query("SELECT * FROM movie_table, movie_trending_table WHERE movieId = trendingMovieId")
	fun getMovieAndTrending(): PagingSource<Int, MovieAndTrending>

	@Transaction
	@Query("SELECT * FROM movie_table, movie_trending_table WHERE movieId = trendingMovieId ORDER BY popularity DESC LIMIT 5")
	fun getCarouselMovies(): PagingSource<Int, MovieAndTrending>

	@Transaction
	@Query("SELECT * FROM movie_table, movie_trending_table WHERE movieId = trendingMovieId LIMIT 8")
	fun getHomeMovieAndTrending(): PagingSource<Int, MovieAndTrending>

}

/*
@Query("SELECT * FROM movie_table JOIN movie_trending_table ON movieId = trendingMovieId LIMIT 8")
Multimap return types approach

https://developer.android.com/training/data-storage/room/relationships

 */