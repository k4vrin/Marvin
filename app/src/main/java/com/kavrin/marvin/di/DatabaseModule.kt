package com.kavrin.marvin.di

import android.app.Application
import androidx.room.Room
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.repository.impl.DataStoreOpImpl
import com.kavrin.marvin.data.repository.impl.LocalDataSourceImpl
import com.kavrin.marvin.domain.repository.DataStoreOp
import com.kavrin.marvin.domain.repository.LocalDataSource
import com.kavrin.marvin.util.Constants.MARVIN_DB
import com.kavrin.marvin.util.PatternChecker
import com.kavrin.marvin.util.PatternCheckerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {



	@Provides
	@Singleton
	fun provideDataStoreOp(
		app: Application,
	): DataStoreOp {
		return DataStoreOpImpl(context = app)
	}

	@Provides
	@Singleton
	fun provideDatabase(
		app: Application
	): MarvinDatabase {
		return Room.databaseBuilder(
			app,
			MarvinDatabase::class.java,
			MARVIN_DB
		).build()
	}

	@Provides
	@Singleton
	fun provideLocalDataSource(
		marvinDatabase: MarvinDatabase
	): LocalDataSource {
		return LocalDataSourceImpl(
			marvinDatabase = marvinDatabase
		)
	}

	@Provides
	@Singleton
	fun providePatternChecker(): PatternChecker = PatternCheckerImpl()

}