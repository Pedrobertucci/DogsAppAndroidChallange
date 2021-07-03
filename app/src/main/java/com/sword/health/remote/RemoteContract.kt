package com.sword.health.remote

import com.sword.health.BuildConfig
import com.sword.health.models.Breed
import com.sword.health.models.Image
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RemoteContract {

    @Headers("Content-Type: application/json")
    @GET("/v1/breeds")
    suspend fun getBreeds(
        @Header("x-api-key") apiKey: String = BuildConfig.apiKey,
        @Query(value = "page", encoded = true) page: Int,
        @Query(value = "limit", encoded = true) limit: Int,
    ): Response<ArrayList<Breed>>

    @Headers("Content-Type: application/json")
    @GET("/v1/breeds/search")
    suspend fun getBreedsByName(
        @Header("x-api-key") apiKey: String = BuildConfig.apiKey,
        @Query(value = "q", encoded = true) query: String
    ): Response<ArrayList<Breed>>

    @Headers("Content-Type: application/json")
    @GET("/v1/breeds/search")
    suspend fun getBreedPhoto(
        @Header("x-api-key") apiKey: String = BuildConfig.apiKey,
        @Query(value = "size", encoded = true) size: String,
        @Query(value = "format", encoded = true) format: String,
        @Query(value = "breed_id", encoded = true) breedId: String,
    ): Response<ArrayList<Image>>

}