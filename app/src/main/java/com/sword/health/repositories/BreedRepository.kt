package com.sword.health.repositories

import com.sword.health.remote.SafeRequest
import com.sword.health.models.Breed
import com.sword.health.models.Image

interface BreedRepository {

    suspend fun getBreeds() : SafeRequest<ArrayList<Breed>>

    suspend fun getBreedsByName(name: String) : SafeRequest<ArrayList<Breed>>

    suspend fun getBreedPhoto(breedId: String) : SafeRequest<ArrayList<Image>>

}