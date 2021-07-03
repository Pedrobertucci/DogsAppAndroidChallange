package com.sword.health.remote

import com.sword.health.BuildConfig
import com.sword.health.models.Breed
import com.sword.health.models.Image
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val request: RemoteContract) {

    suspend fun getBreeds(page: Int, limit: Int): Response<ArrayList<Breed>> {
        return request.getBreeds(page = page,
                                 limit = limit)
    }

    suspend fun getBreedsByName(query: String): Response<ArrayList<Breed>> {
        return request.getBreedsByName(query = query)
    }

    suspend fun getBreedPhoto(breadId: String): Response<ArrayList<Image>> {
          return request.getBreedPhoto(size = "small",
                                       format = "json",
                                       breedId = breadId)
    }
}