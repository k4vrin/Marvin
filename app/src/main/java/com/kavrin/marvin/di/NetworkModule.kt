package com.kavrin.marvin.di

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.remote.IMDbService
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.data.remote.TMDBTvService
import com.kavrin.marvin.data.repository.impl.IMDbDataSourceImpl
import com.kavrin.marvin.data.repository.impl.MovieRemoteDataSourceImpl
import com.kavrin.marvin.data.repository.impl.TvRemoteDataSourceImpl
import com.kavrin.marvin.domain.repository.IMDbDataSource
import com.kavrin.marvin.domain.repository.MovieRemoteDataSource
import com.kavrin.marvin.domain.repository.TvRemoteDataSource
import com.kavrin.marvin.util.Constants.BASE_URL
import com.kavrin.marvin.util.NetworkListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideHttpClient(): OkHttpClient {
		val loggingInterceptor = HttpLoggingInterceptor()
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
		return OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.connectTimeout(15, TimeUnit.SECONDS)
			.callTimeout(15, TimeUnit.SECONDS)
			.writeTimeout(15, TimeUnit.SECONDS)
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
	fun provideIMDbService(
		retrofit: Retrofit
	): IMDbService {
		return retrofit.create(IMDbService::class.java)
	}

	@Provides
	@Singleton
	fun provideMovieRemoteDataSource(
		movieService: TMDBMovieService,
		marvinDatabase: MarvinDatabase
	): MovieRemoteDataSource {
		return MovieRemoteDataSourceImpl(
			movieService = movieService,
			marvinDatabase = marvinDatabase
		)
	}

	@Provides
	@Singleton
	fun provideTvRemoteDataSource(
		tvService: TMDBTvService,
		marvinDatabase: MarvinDatabase
	): TvRemoteDataSource {
		return TvRemoteDataSourceImpl(
			tvService = tvService,
			marvinDatabase = marvinDatabase
		)
	}

	@Provides
	@Singleton
	fun provideIMDbDataSource(
		imdbService: IMDbService,
		marvinDatabase: MarvinDatabase
	): IMDbDataSource {
		return IMDbDataSourceImpl(
			imdbService = imdbService,
			marvinDatabase = marvinDatabase
		)
	}

	@Provides
	@Singleton
	fun provideNetworkListener(
		application: Application
	) = NetworkListener(application)
}