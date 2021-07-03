package com.sword.health.di

import com.sword.health.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.sword.health.remote.RemoteDataSource

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideLogInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
       return  OkHttpClient.Builder()
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(provideLogInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .baseUrl(BuildConfig.baseUrl)
            .build()
            .create(RemoteDataSource::class.java)
    }
}