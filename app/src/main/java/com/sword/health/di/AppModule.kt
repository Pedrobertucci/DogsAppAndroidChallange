package com.sword.health.di

import android.app.Application
import com.sword.health.BuildConfig
import com.sword.health.remote.RemoteDataSource
import com.sword.health.repositories.BreedRepository
import com.sword.health.repositories.DefaultRepository
import com.sword.health.view.utils.Utils
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideCache(context: Application) : Cache {
        return Cache(context.cacheDir, (10 * 1024 * 1024).toLong())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Application): OkHttpClient {
       return OkHttpClient.Builder()
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .cache(provideCache(context))
            .addInterceptor(provideLoggingInterceptor())
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