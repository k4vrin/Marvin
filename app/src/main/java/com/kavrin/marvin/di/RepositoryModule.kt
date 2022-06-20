package com.kavrin.marvin.di

import android.app.Application
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.data.repository.impl.DataStoreOpImpl
import com.kavrin.marvin.domain.repository.DataStoreOp
import com.kavrin.marvin.domain.use_cases.home.*
import com.kavrin.marvin.domain.use_cases.splash_welcome.ReadOnBoarding
import com.kavrin.marvin.domain.use_cases.splash_welcome.SaveOnBoarding
import com.kavrin.marvin.domain.use_cases.splash_welcome.SplashWelcomeUseCases
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
			saveOnBoarding = SaveOnBoarding(repository)
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
			getHomeTrendingTvs = GetHomeTrendingTvs(repository)
		)
	}
}