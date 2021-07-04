package com.sword.health.di

import android.app.Application
import com.sword.health.BuildConfig
import com.sword.health.remote.RemoteDataSource
import com.sword.health.repositories.BreedRepository
import com.sword.health.repositories.DefaultRepository
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideLogInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideCache(context: Application) : Cache {
        val httpCacheDir = File(context.cacheDir, "responseCache")
        return Cache(httpCacheDir, (10 * 1024 * 1024).toLong() )
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Application): OkHttpClient {
       return OkHttpClient.Builder()
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(provideLogInterceptor())
            .cache(provideCache(context))
            .build()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(context: Application): RemoteDataSource {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(context))
            .baseUrl(BuildConfig.baseUrl)
            .build()
            .create(RemoteDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideBreedRepository(context: Application): BreedRepository {
        return DefaultRepository(provideRemoteDataSource(context))
    }
}