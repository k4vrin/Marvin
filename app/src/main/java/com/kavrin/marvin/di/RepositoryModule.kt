package com.kavrin.marvin.di

import android.app.Application
import com.kavrin.marvin.data.repository.Repository
import com.kavrin.marvin.data.repository.impl.DataStoreOpImpl
import com.kavrin.marvin.domain.repository.DataStoreOp
import com.kavrin.marvin.domain.use_cases.ReadOnBoardingUseCase
import com.kavrin.marvin.domain.use_cases.SaveOnBoardingUseCase
import com.kavrin.marvin.domain.use_cases.UseCases
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
		app: Application
	): DataStoreOp {
		return DataStoreOpImpl(context = app)
	}

	@Provides
	@Singleton
	fun provideUseCases(
		repository: Repository
	): UseCases {
		return UseCases(
			saveOnBoardingUseCase = SaveOnBoardingUseCase(repository = repository),
			readOnBoardingUseCase = ReadOnBoardingUseCase(repository = repository)
		)
	}
}