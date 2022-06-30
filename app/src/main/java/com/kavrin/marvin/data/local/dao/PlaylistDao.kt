package com.kavrin.marvin.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.playlist.Playlist
import com.kavrin.marvin.domain.model.playlist.MoviePlaylistCrossRef
import com.kavrin.marvin.domain.model.playlist.MovieWithPlaylists
import com.kavrin.marvin.domain.model.playlist.PlaylistWithMovies
import com.kavrin.marvin.domain.model.playlist.PlaylistWithTvs
import com.kavrin.marvin.domain.model.playlist.TvPlaylistCrossRef
import com.kavrin.marvin.domain.model.playlist.TvWithPlaylists

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