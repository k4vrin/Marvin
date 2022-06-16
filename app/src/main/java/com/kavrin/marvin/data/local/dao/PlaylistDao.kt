package com.kavrin.marvin.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.Playlist
import com.kavrin.marvin.domain.model.movie.entities.relations.MoviePlaylistCrossRef
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieWithPlaylists
import com.kavrin.marvin.domain.model.movie.entities.relations.PlaylistWithMovies
import com.kavrin.marvin.domain.model.tv.entities.relations.PlaylistWithTvs
import com.kavrin.marvin.domain.model.tv.entities.relations.TvPlaylistCrossRef
import com.kavrin.marvin.domain.model.tv.entities.relations.TvWithPlaylists

@Dao
interface PlaylistDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertPlaylist(playlist: Playlist)

	///////////////////////////////////////////////////////////////////////////
	// Movie
	///////////////////////////////////////////////////////////////////////////

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMoviePlaylistCrossRef(crossRef: MoviePlaylistCrossRef)

	@Transaction
	@Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
	fun getMoviesOfPlaylist(playlistId: Int): PagingSource<Int, PlaylistWithMovies>

	@Transaction
	@Query("SELECT * FROM movie_table WHERE movieId = :movieId")
	fun getPlaylistsOfMovie(movieId: Int): PagingSource<Int, MovieWithPlaylists>

	///////////////////////////////////////////////////////////////////////////
	// Tv
	///////////////////////////////////////////////////////////////////////////

	@Transaction
	@Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
	fun getTvsOfPlaylist(playlistId: Int): PagingSource<Int, PlaylistWithTvs>

	@Transaction
	@Query("SELECT * FROM tv_table WHERE tvId = :tvId")
	fun getPlaylistsOfTv(tvId: Int): PagingSource<Int, TvWithPlaylists>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMoviePlaylistCrossRef(crossRef: TvPlaylistCrossRef)
}