package com.sword.health.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sword.health.models.Breed
import com.sword.health.models.Image
import com.sword.health.remote.Status
import com.sword.health.repositories.BreedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BreedViewModel @Inject constructor(private val repository: BreedRepository) : ViewModel() {
    private val breedsMutableData = MutableLiveData<ArrayList<Breed>>()
    val breedsLiveData: LiveData<ArrayList<Breed>> get() = breedsMutableData

    private val photoMutableData = MutableLiveData<ArrayList<Image>>()
    val photoLiveData: LiveData<ArrayList<Image>> get() = photoMutableData

    private val loadingMutableData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = loadingMutableData

    private val errorMutableData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = errorMutableData

    fun getBreeds(page: Int) {
            enableLoading()
            viewModelScope.launch {
                val response = repository.getBreeds(page)
                disableLoading()

                if (response.status == Status.SUCCESS)
                    breedsMutableData.value = response.data
                else
                    errorMutableData.value = response.message
            }
    }

    private suspend fun disableLoading() = withContext(Dispatchers.Main) {
        loadingMutableData.value = false
    }

    private fun enableLoading()  {
        loadingMutableData.value = true
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