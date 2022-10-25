package com.kavrin.marvin.di

import com.kavrin.marvin.data.repository.Repository
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
import com.kavrin.marvin.domain.use_cases.tv_season.GetTvSeason
import com.kavrin.marvin.domain.use_cases.tv_season.TvSeasonUseCases
import com.kavrin.marvin.util.DefaultDispatchers
import com.kavrin.marvin.util.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

	@Provides
	@Singleton
	fun provideDispatchers(): DispatchersProvider = DefaultDispatchers()

	@Provides
	@ViewModelScoped
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
	@ViewModelScoped
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
	@ViewModelScoped
	fun provideMovieUseCases(
		repository: Repository,
		dispatchers: DispatchersProvider
	): MovieUseCases {
		return MovieUseCases(
			getMovie = GetMovie(repository),
			getMovieDetails = GetMovieDetails(repository, dispatchers),
			getMovieRatings = GetMovieRatings(repository),
			getCollection = GetCollection(repository)
		)
	}

	@Provides
	@ViewModelScoped
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
	@ViewModelScoped
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
	@ViewModelScoped
	fun providePersonUseCases(
		repository: Repository
	): PersonUseCases {
		return PersonUseCases(
			getPersonDetails = GetPersonDetails(repository)
		)
	}

	@Provides
	@ViewModelScoped
	fun provideSearchUseCases(
		repository: Repository
	): SearchUseCases {
		return SearchUseCases(
			searchMovies = SearchMovies(repository),
			searchTvs = SearchTvs(repository)
		)
	}

	@Provides
	@ViewModelScoped
	fun provideSeasonUseCases(
		repository: Repository
	): TvSeasonUseCases {
		return TvSeasonUseCases(
			getTvSeason = GetTvSeason(repository)
		)
	}
}