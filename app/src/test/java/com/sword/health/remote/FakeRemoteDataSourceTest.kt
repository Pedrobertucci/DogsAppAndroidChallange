package com.sword.health.remote

import com.sword.health.models.Breed
import com.sword.health.models.Image
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query
import kotlin.collections.ArrayList

class FakeRemoteDataSourceTest : RemoteDataSource {
    var shouldReturnNetworkError = false
    var shouldReturnEmptyValues = false

    override suspend fun getBreeds(
        apiKey: String,
        page: Int,
        limit: Int
    ): Response<ArrayList<Breed>> {
        return when {
            shouldReturnNetworkError -> {
                val errorResponseBody = "".toResponseBody("application/json".toMediaTypeOrNull())
                Response.error(500, errorResponseBody)
            }

            shouldReturnEmptyValues -> {
                Response.success(ArrayList())
            }

            else -> {
                val result = ArrayList<Breed>()
                result.add(Breed())
                Response.success(result)
            }
        }
    }

    override suspend fun getBreedsByName(
        apiKey: String,
        query: String
    ): Response<ArrayList<Breed>> {
        return when {
            shouldReturnNetworkError -> {
                val errorResponseBody = "".toResponseBody("application/json".toMediaTypeOrNull())
                Response.error(500, errorResponseBody)
            }

            shouldReturnEmptyValues -> {
                Response.success(ArrayList())
            }

            else -> {
                val result = ArrayList<Breed>()
                result.add(Breed())
                Response.success(result)
            }
        }
    }

    override suspend fun getBreedPhoto(
        @Header(value = "x-api-key") apiKey: String,
        @Query(encoded = true, value = "breed_id") breedId: String,
        @Query(encoded = true, value = "format") format: String,
        @Query(encoded = true, value = "limit") limit: Int,
        @Query(encoded = true, value = "page") page: Int,
        @Query(encoded = true, value = "size") size: String
    ): Response<java.util.ArrayList<Image>> {
        return when {
            shouldReturnNetworkError -> {
                val errorResponseBody = "".toResponseBody("application/json".toMediaTypeOrNull())
                Response.error(500, errorResponseBody)
            }

            shouldReturnEmptyValues -> {
                Response.success(ArrayList())
            }

            else -> {
                val result = ArrayList<Image>()
                result.add(Image())
                Response.success(result)
            }
        }
    }
}