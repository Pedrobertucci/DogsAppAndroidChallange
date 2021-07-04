package com.sword.health.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sword.health.models.Breed
import com.sword.health.models.Image
import com.sword.health.remote.Status
import com.sword.health.repositories.BreedRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class BreedViewModel @Inject constructor(private val repository: BreedRepository) : ViewModel() {
    private var emptyBreeds = false

    private val breedsMutableData = MutableLiveData<ArrayList<Breed>>()
    val breedsLiveData: LiveData<ArrayList<Breed>> get() = breedsMutableData

    private val photoMutableData = MutableLiveData<ArrayList<Image>>()
    val photoLiveData: LiveData<ArrayList<Image>> get() = photoMutableData

    private val loadingMutableData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = loadingMutableData

    private val errorMutableData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = errorMutableData

    fun getBreeds(page: Int) {
        if (!emptyBreeds) {
            loadingMutableData.value = true
            viewModelScope.launch {
                val response = repository.getBreeds(page)

                if (response.status == Status.SUCCESS) {
                    response.data?.let {
                       if (it.isEmpty()) {
                           disableGetBreeds()
                       } else {
                           enableGetBreeds()
                           loadingMutableData.value = false
                           breedsMutableData.value = response.data
                       }
                    } ?: disableGetBreeds()

                } else {
                    loadingMutableData.value = false
                    errorMutableData.value = response.message
                }
            }
        } else {
            loadingMutableData.value = false
        }
    }

    private fun disableGetBreeds() {
        emptyBreeds = true
    }

    private fun enableGetBreeds() {
        emptyBreeds = false
    }

    fun getBreedsByName(name: String)  {
        viewModelScope.launch {
            val response = repository.getBreedsByName(name = name)

            if(response.status == Status.SUCCESS) {
                breedsMutableData.value = response.data

            } else {
                errorMutableData.value = response.message
            }
        }
    }

    fun getBreedPhoto(breedId: String)  {
        viewModelScope.launch {
            val response = repository.getBreedPhoto(breedId = breedId)

            if(response.status == Status.SUCCESS) {
                photoMutableData.value = response.data

            } else {
                errorMutableData.value = response.message
            }
        }
    }
}