package com.kavrin.marvin.di

import android.app.Application
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.data.repository.impl.DataStoreOpImpl
import com.kavrin.marvin.domain.repository.DataStoreOp
import com.kavrin.marvin.domain.use_cases.ReadOnBoardingUseCase
import com.kavrin.marvin.domain.use_cases.SaveOnBoardingUseCase
import com.kavrin.marvin.domain.use_cases.UseCases
import com.kavrin.marvin.domain.use_cases.movie.GetPopularMoviesUseCase
import com.kavrin.marvin.domain.use_cases.movie.GetTopRatedMoviesUseCase
import com.kavrin.marvin.domain.use_cases.movie.GetTrendingMoviesUseCase
import com.kavrin.marvin.domain.use_cases.movie.GetCarouselMoviesUseCase
import com.kavrin.marvin.domain.use_cases.tv.GetPopularTvsUseCase
import com.kavrin.marvin.domain.use_cases.tv.GetTopRatedTvsUseCase
import com.kavrin.marvin.domain.use_cases.tv.GetTrendingTvsUseCase
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
	fun provideUseCases(
		repository: Repository,
	): UseCases {
		return UseCases(
			saveOnBoardingUseCase = SaveOnBoardingUseCase(repository = repository),
			readOnBoardingUseCase = ReadOnBoardingUseCase(repository = repository),
			getPopularMoviesUseCase = GetPopularMoviesUseCase(repository = repository),
			getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(repository = repository),
			getTrendingMoviesUseCase = GetTrendingMoviesUseCase(repository = repository),
			carouselUseCase = GetCarouselMoviesUseCase(repository = repository),
			getPopularTvsUseCase = GetPopularTvsUseCase(repository = repository),
			getTopRatedTvsUseCase = GetTopRatedTvsUseCase(repository = repository),
			getTrendingTvsUseCase = GetTrendingTvsUseCase(repository = repository)
		)
	}
}