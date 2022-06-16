package com.kavrin.marvin.di

import android.app.Application
import androidx.room.Room
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.util.Constants.MARVIN_DB
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
	fun provideDatabase(
		app: Application
	): MarvinDatabase {
		return Room.databaseBuilder(
			app,
			MarvinDatabase::class.java,
			MARVIN_DB
		).build()
	}

}