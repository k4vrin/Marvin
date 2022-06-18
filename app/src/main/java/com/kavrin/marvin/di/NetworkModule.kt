package com.kavrin.marvin.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.data.remote.TMDBTvService
import com.kavrin.marvin.data.repository.impl.RemoteDataSourceImpl
import com.kavrin.marvin.domain.repository.RemoteDataSource
import com.kavrin.marvin.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideHttpClient(): OkHttpClient {
		return OkHttpClient.Builder()
			.connectTimeout(15, TimeUnit.SECONDS)
			.readTimeout(15, TimeUnit.SECONDS)
			.build()
	}

	@Provides
	@Singleton
	fun provideRetrofit(
		okHttpClient: OkHttpClient
	): Retrofit {
		val contentType = "application/json".toMediaType()
		val json = Json {
			ignoreUnknownKeys = true
		}
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(json.asConverterFactory(contentType))
			.build()
	}

	@Provides
	@Singleton
	fun provideTMDBMovieService(
		retrofit: Retrofit
	): TMDBMovieService {
		return retrofit.create(TMDBMovieService::class.java)
	}

	@Provides
	@Singleton
	fun provideTMDBTvService(
		retrofit: Retrofit
	): TMDBTvService {
		return retrofit.create(TMDBTvService::class.java)
	}

	@Provides
	@Singleton
	fun provideRemoteDataSource(
		movieService: TMDBMovieService,
		tvService: TMDBTvService,
		marvinDatabase: MarvinDatabase
	): RemoteDataSource {
		return RemoteDataSourceImpl(
			movieService = movieService,
			tvService = tvService,
			marvinDatabase = marvinDatabase
		)
	}
}