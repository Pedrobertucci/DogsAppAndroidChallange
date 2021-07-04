package com.sword.health.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sword.health.MainCoroutineRule
import com.sword.health.repositories.FakeBreedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BreedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: BreedViewModel

    private val fakeBreedRepository = FakeBreedRepository()

    @Before
    fun setUp() {
        viewModel = BreedViewModel(fakeBreedRepository)
    }

    @Test
    fun `should fetch breeds and return a error`() {
        fakeBreedRepository.shouldReturnNetworkError = true
        viewModel.getBreeds(1)

        val value = viewModel.errorLiveData.getOrAwaitValueTest()

        assert(value.isNotEmpty())
    }

    @Test
    fun `should fetch breeds and return a breed`() {
        fakeBreedRepository.shouldReturnNetworkError = false
        fakeBreedRepository.shouldReturnEmptyValues = false

        viewModel.getBreeds(1)

        val value = viewModel.breedsLiveData.getOrAwaitValueTest()

        assert(value.size == 1)
    }

    @Test
    fun `should fetch breeds and return empty`() {
        fakeBreedRepository.shouldReturnNetworkError = false
        fakeBreedRepository.shouldReturnEmptyValues = true

        viewModel.getBreeds(1)

        val value = viewModel.breedsLiveData.getOrAwaitValueTest()

        assert(value.size == 0)
    }

    @Test
    fun `should fetch breeds by name and return a breed`() {
        fakeBreedRepository.shouldReturnNetworkError = false
        fakeBreedRepository.shouldReturnEmptyValues = false

        viewModel.getBreedsByName("test")

        val value = viewModel.breedsLiveData.getOrAwaitValueTest()

        assert(value.size == 1)
    }

    @Test
    fun `should fetch breeds by name and return a error`() {
        fakeBreedRepository.shouldReturnNetworkError = true
        viewModel.getBreedsByName("test")

        val value = viewModel.errorLiveData.getOrAwaitValueTest()

        assert(value.isNotEmpty())
    }

    @Test
    fun `should fetch breeds by name repository and return empty`() {
        fakeBreedRepository.shouldReturnNetworkError = false
        fakeBreedRepository.shouldReturnEmptyValues = true

        viewModel.getBreedsByName("test")

        val value = viewModel.breedsLiveData.getOrAwaitValueTest()

        assert(value.size == 0)
    }

    @Test
    fun `should fetch photo and return a error`() {
        fakeBreedRepository.shouldReturnNetworkError = true
        fakeBreedRepository.shouldReturnEmptyValues = false

        viewModel.getBreedPhoto("12345")

        val value = viewModel.errorLiveData.getOrAwaitValueTest()

        assert(value.isNotEmpty())
    }

    @Test
    fun `should fetch photo and return empty`() {
        fakeBreedRepository.shouldReturnNetworkError = false
        fakeBreedRepository.shouldReturnEmptyValues = true

        viewModel.getBreedPhoto("12345")

        val value = viewModel.photoLiveData.getOrAwaitValueTest()

        assert(value.size == 0)
    }

    @Test
    fun `should fetch photo and return a photo`() {
        fakeBreedRepository.shouldReturnNetworkError = false
        fakeBreedRepository.shouldReturnEmptyValues = false

        viewModel.getBreedPhoto("12345")

        val value = viewModel.photoLiveData.getOrAwaitValueTest()

        assert(value.size == 1)
    }
}