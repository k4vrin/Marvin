package com.kavrin.marvin.di

import android.app.Application
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.data.repository.impl.DataStoreOpImpl
import com.kavrin.marvin.domain.repository.DataStoreOp
import com.kavrin.marvin.domain.use_cases.home.*
import com.kavrin.marvin.domain.use_cases.list.*
import com.kavrin.marvin.domain.use_cases.movie.*
import com.kavrin.marvin.domain.use_cases.person.GetPersonDetails
import com.kavrin.marvin.domain.use_cases.person.PersonUseCases
import com.kavrin.marvin.domain.use_cases.search.SearchMovies
import com.kavrin.marvin.domain.use_cases.search.SearchTvs
import com.kavrin.marvin.domain.use_cases.search.SearchUseCases
import com.kavrin.marvin.domain.use_cases.splash_welcome.ReadOnBoarding
import com.kavrin.marvin.domain.use_cases.splash_welcome.SaveGenres
import com.kavrin.marvin.domain.use_cases.splash_welcome.SaveOnBoarding
import com.kavrin.marvin.domain.use_cases.splash_welcome.SplashWelcomeUseCases
import com.kavrin.marvin.domain.use_cases.tv.GetTv
import com.kavrin.marvin.domain.use_cases.tv.GetTvDetails
import com.kavrin.marvin.domain.use_cases.tv.GetTvRatings
import com.kavrin.marvin.domain.use_cases.tv.TvUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

	@Provides
	@Singleton
	fun provideDataStoreOp(
		app: Application,
	): DataStoreOp {
		return DataStoreOpImpl(context = app)
	}

	@Provides
	@Singleton
	fun provideSplashAndWelcomeUseCases(
		repository: Repository
	): SplashWelcomeUseCases {
		return SplashWelcomeUseCases(
			readOnBoarding = ReadOnBoarding(repository),
			saveOnBoarding = SaveOnBoarding(repository),
			saveGenres = SaveGenres(repository)
		)
	}

	@Provides
	@Singleton
	fun provideHomeUseCases(
		repository: Repository
	): HomeUseCases {
		return HomeUseCases(
			getCarouselMovies = GetCarouselMovies(repository),
			getHomePopularMovies = GetHomePopularMovies(repository),
			getHomeTopRatedMovies = GetHomeTopRatedMovies(repository),
			getHomeTrendingMovies = GetHomeTrendingMovies(repository),
			getCarouselTvs = GetCarouselTvs(repository),
			getHomePopularTvs = GetHomePopularTvs(repository),
			getHomeTopRatedTvs = GetHomeTopRatedTvs(repository),
			getHomeTrendingTvs = GetHomeTrendingTvs(repository),
			deleteAll = DeleteAll(repository)
		)
	}

	@Provides
	@Singleton
	fun provideMovieUseCases(
		repository: Repository
	): MovieUseCases {
		return MovieUseCases(
			getMovie = GetMovie(repository),
			getMovieDetails = GetMovieDetails(repository),
			getMovieRatings = GetMovieRatings(repository),
			getCollection = GetCollection(repository)
		)
	}

	@Provides
	@Singleton
	fun provideTvUseCases(
		repository: Repository
	): TvUseCases {
		return TvUseCases(
			getTv = GetTv(repository),
			getTvDetails = GetTvDetails(repository),
			getTvRatings = GetTvRatings(repository)
		)
	}

	@Provides
	@Singleton
	fun provideListUseCases(
		repository: Repository
	): ListUseCases {
		return ListUseCases(
			getPopularMoviesUseCase = GetPopularMoviesUseCase(repository),
			getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(repository),
			getTrendingMoviesUseCase = GetTrendingMoviesUseCase(repository),
			getPopularTvsUseCase = GetPopularTvsUseCase(repository),
			getTopRatedTvsUseCase = GetTopRatedTvsUseCase(repository),
			getTrendingTvsUseCase = GetTrendingTvsUseCase(repository)
		)
	}

	@Provides
	@Singleton
	fun providePersonUseCases(
		repository: Repository
	): PersonUseCases {
		return PersonUseCases(
			getPersonDetails = GetPersonDetails(repository)
		)
	}

	@Provides
	@Singleton
	fun provideSearchUseCases(
		repository: Repository
	): SearchUseCases {
		return SearchUseCases(
			searchMovies = SearchMovies(repository),
			searchTvs = SearchTvs(repository)
		)
	}
}