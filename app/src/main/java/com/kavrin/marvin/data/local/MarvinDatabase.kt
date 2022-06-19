package com.kavrin.marvin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kavrin.marvin.data.local.dao.*
import com.kavrin.marvin.data.local.dao.movie.MovieDao
import com.kavrin.marvin.data.local.dao.movie.MoviePopularDao
import com.kavrin.marvin.data.local.dao.movie.MovieTopRatedDao
import com.kavrin.marvin.data.local.dao.movie.MovieTrendingDao
import com.kavrin.marvin.data.local.dao.movie.remote_keys.MoviePopularRemoteKeysDao
import com.kavrin.marvin.data.local.dao.movie.remote_keys.MovieTopRatedRemoteKeysDao
import com.kavrin.marvin.data.local.dao.movie.remote_keys.MovieTrendingRemoteKeysDao
import com.kavrin.marvin.data.local.dao.tv.TvDao
import com.kavrin.marvin.data.local.dao.tv.TvPopularDao
import com.kavrin.marvin.data.local.dao.tv.TvTopRatedDao
import com.kavrin.marvin.data.local.dao.tv.TvTrendingDao
import com.kavrin.marvin.data.local.dao.tv.remote_keys.TvPopularRemoteKeysDao
import com.kavrin.marvin.data.local.dao.tv.remote_keys.TvTopRatedRemoteKeysDao
import com.kavrin.marvin.data.local.dao.tv.remote_keys.TvTrendingRemoteKeysDao
import com.kavrin.marvin.domain.model.Playlist
import com.kavrin.marvin.domain.model.movie.entities.relations.MoviePlaylistCrossRef
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.MoviePopular
import com.kavrin.marvin.domain.model.movie.entities.MovieTopRated
import com.kavrin.marvin.domain.model.movie.entities.MovieTrending
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MoviePopularRemoteKeys
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MovieTopRatedRemoteKeys
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MovieTrendingRemoteKeys
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.TvPopular
import com.kavrin.marvin.domain.model.tv.entities.TvTopRated
import com.kavrin.marvin.domain.model.tv.entities.TvTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvPlaylistCrossRef
import com.kavrin.marvin.domain.model.tv.entities.remote_keys.TvPopularRemoteKeys
import com.kavrin.marvin.domain.model.tv.entities.remote_keys.TvTopRatedRemoteKeys
import com.kavrin.marvin.domain.model.tv.entities.remote_keys.TvTrendingRemoteKeys

@Database(
	entities = [
		Playlist::class,
		Movie::class,
		MoviePopular::class,
		MovieTopRated::class,
		MovieTrending::class,
		MoviePlaylistCrossRef::class,
		MoviePopularRemoteKeys::class,
		MovieTopRatedRemoteKeys::class,
		MovieTrendingRemoteKeys::class,
		Tv::class,
		TvPopular::class,
		TvTopRated::class,
		TvTrending::class,
		TvPlaylistCrossRef::class,
		TvPopularRemoteKeys::class,
		TvTopRatedRemoteKeys::class,
		TvTrendingRemoteKeys::class
	],
	version = 1,
	exportSchema = true
)
@TypeConverters(DatabaseConverter::class)
abstract class MarvinDatabase : RoomDatabase() {

	abstract fun playlistDao(): PlaylistDao

	abstract fun movieDao(): MovieDao
	abstract fun moviePopularDao(): MoviePopularDao
	abstract fun movieTopRatedDao(): MovieTopRatedDao
	abstract fun movieTrendingDao(): MovieTrendingDao

	abstract fun tvDao(): TvDao
	abstract fun tvPopularDao(): TvPopularDao
	abstract fun tvTopRatedDao(): TvTopRatedDao
	abstract fun tvTrendingDao(): TvTrendingDao

	abstract fun moviePopularRemoteKeysDao(): MoviePopularRemoteKeysDao
	abstract fun movieTopRatedRemoteKeysDao(): MovieTopRatedRemoteKeysDao
	abstract fun movieTrendingRemoteKeysDao(): MovieTrendingRemoteKeysDao

	abstract fun tvPopularRemoteKeysDao(): TvPopularRemoteKeysDao
	abstract fun tvTopRatedRemoteKeysDao(): TvTopRatedRemoteKeysDao
	abstract fun tvTrendingRemoteKeysDao(): TvTrendingRemoteKeysDao
}