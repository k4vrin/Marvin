package com.kavrin.marvin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kavrin.marvin.data.local.dao.*
import com.kavrin.marvin.data.local.dao.movie.MovieDao
import com.kavrin.marvin.data.local.dao.movie.MoviePopularDao
import com.kavrin.marvin.data.local.dao.movie.MovieTopRatedDao
import com.kavrin.marvin.data.local.dao.movie.MovieTrendingDao
import com.kavrin.marvin.data.local.dao.tv.TvDao
import com.kavrin.marvin.data.local.dao.tv.TvPopularDao
import com.kavrin.marvin.data.local.dao.tv.TvTopRatedDao
import com.kavrin.marvin.data.local.dao.tv.TvTrendingDao
import com.kavrin.marvin.domain.model.Playlist
import com.kavrin.marvin.domain.model.movie.entities.relations.MoviePlaylistCrossRef
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.movie.entities.MoviePopular
import com.kavrin.marvin.domain.model.movie.entities.MovieTopRated
import com.kavrin.marvin.domain.model.movie.entities.MovieTrending
import com.kavrin.marvin.domain.model.tv.entities.Tv
import com.kavrin.marvin.domain.model.tv.entities.TvPopular
import com.kavrin.marvin.domain.model.tv.entities.TvTopRated
import com.kavrin.marvin.domain.model.tv.entities.TvTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvPlaylistCrossRef

@Database(
	entities = [
		Playlist::class,
		Movie::class,
		MoviePopular::class,
		MovieTopRated::class,
		MovieTrending::class,
		MoviePlaylistCrossRef::class,
		Tv::class,
		TvPopular::class,
		TvTopRated::class,
		TvTrending::class,
		TvPlaylistCrossRef::class
	],
	version = 1,
	exportSchema = true
)
@TypeConverters(DatabaseConverter::class)
abstract class MarvinDatabase : RoomDatabase() {

	abstract val playlistDao: PlaylistDao

	abstract val movieDao: MovieDao
	abstract val moviePopularDao: MoviePopularDao
	abstract val movieTopRatedDao: MovieTopRatedDao
	abstract val movieTrendingDao: MovieTrendingDao

	abstract val tvDao: TvDao
	abstract val tvPopularDao: TvPopularDao
	abstract val tvTopRatedDao: TvTopRatedDao
	abstract val tvTrendingDao: TvTrendingDao


}