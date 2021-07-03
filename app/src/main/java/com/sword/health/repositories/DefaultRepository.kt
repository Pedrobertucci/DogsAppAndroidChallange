package com.sword.health.repositories

import com.sword.health.models.Breed
import com.sword.health.models.Image
import com.sword.health.remote.RemoteDataSource
import com.sword.health.remote.SafeRequest
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource) : BreedRepository {
    private var page = 1

    override suspend fun getBreeds(): SafeRequest<ArrayList<Breed>> {
        return try {
            val response = remoteDataSource.getBreeds(page = page)
            if (response.isSuccessful) {
                response.body()?.let {
                    page++
                    return@let SafeRequest.success(it)
                } ?: SafeRequest.error("An unknown error", null)
            } else {
                SafeRequest.error("An unknown error", null)
            }
        } catch (e: Exception) {
            SafeRequest.error("Check your internet connection", null)
        }
    }

    override suspend fun getBreedsByName(name: String): SafeRequest<ArrayList<Breed>> {
        return try {
            val response = remoteDataSource.getBreedsByName(query = name)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let SafeRequest.success(it)
                } ?: SafeRequest.error("An unknown error", null)
            } else {
                SafeRequest.error("An unknown error", null)
            }
        } catch (e: Exception) {
            SafeRequest.error("Check your internet connection", null)
        }
    }

    override suspend fun getBreedPhoto(breedId: String): SafeRequest<ArrayList<Image>> {
        return try {
            val response = remoteDataSource.getBreedPhoto(breedId = breedId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let SafeRequest.success(it)
                } ?: SafeRequest.error("An unknown error", null)
            } else {
                SafeRequest.error("An unknown error", null)
            }
        } catch (e: Exception) {
            SafeRequest.error("Check your internet connection", null)
        }
    }
}