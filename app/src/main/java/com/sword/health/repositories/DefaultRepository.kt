package com.sword.health.repositories

import com.sword.health.models.Breed
import com.sword.health.models.Image
import com.sword.health.remote.RemoteDataSource
import com.sword.health.remote.SafeRequest
import retrofit2.Response
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource) : BreedRepository {

    override suspend fun getBreeds(page: Int): SafeRequest<ArrayList<Breed>> {
        val response : Response<ArrayList<Breed>>
        return try {
            response = remoteDataSource.getBreeds(page = page)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let SafeRequest.success(it)
                } ?: SafeRequest.error("An unknown error", null)
            } else {
                return SafeRequest.error("An unknown error", null)
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