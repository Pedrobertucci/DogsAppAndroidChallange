package com.sword.health.repositories

import com.sword.health.models.Breed
import com.sword.health.models.Image
import com.sword.health.remote.SafeRequest
import java.util.*

class FakeBreedRepository : BreedRepository {

    var shouldReturnNetworkError = false
    var shouldReturnEmptyValues = false

    override suspend fun getBreeds(page: Int): SafeRequest<ArrayList<Breed>> {
        return when {
            shouldReturnNetworkError -> {
                SafeRequest.error("Error", null)
            }
            shouldReturnEmptyValues -> {
                SafeRequest.success(ArrayList<Breed>())
            }
            else -> {
                val breeds = ArrayList<Breed>()
                breeds.add(Breed())
                SafeRequest.success(breeds)
            }
        }
    }

    override suspend fun getBreedsByName(name: String): SafeRequest<ArrayList<Breed>> {
        return when {
            shouldReturnNetworkError -> {
                SafeRequest.error("Error", null)
            }
            shouldReturnEmptyValues -> {
                SafeRequest.success(ArrayList<Breed>())
            }
            else -> {
                val breeds = ArrayList<Breed>()
                breeds.add(Breed())
                SafeRequest.success(breeds)
            }
        }
    }

    override suspend fun getBreedPhoto(breedId: String): SafeRequest<ArrayList<Image>> {
        return when {
            shouldReturnNetworkError -> {
                SafeRequest.error("Error", null)
            }
            shouldReturnEmptyValues -> {
                SafeRequest.success(ArrayList<Image>())
            }
            else -> {
                val images = ArrayList<Image>()
                images.add(Image())
                SafeRequest.success(images)
            }
        }
    }
}