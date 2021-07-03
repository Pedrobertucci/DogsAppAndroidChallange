package com.sword.health.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sword.health.remote.FakeRemoteDataSource
import com.sword.health.remote.Status
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var defaultRepository: DefaultRepository

    private var remoteDataSource = FakeRemoteDataSource()

    @Before
    fun setUp() {
        defaultRepository = DefaultRepository(remoteDataSource)
    }

    @Test
    fun shouldFetchBreedsAndReturnError() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = true
        remoteDataSource.shouldReturnEmptyValues = false

        val result = defaultRepository.getBreeds()

        assert(result.status == Status.ERROR)
    }

    @Test
    fun shouldFetchBreedsAndReturnOneBreed() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = false

        val result = defaultRepository.getBreeds()

        assert(result.status == Status.SUCCESS)
        assert(result.data?.size == 1)
    }

    @Test
    fun shouldFetchBreedsAndReturnEmpty() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = true

        val result = defaultRepository.getBreeds()

        assert(result.status == Status.SUCCESS)
        assert(result.data?.size == 0)
    }

    @Test
    fun shouldFetchBreedsByNameAndReturnOneBreed() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = false

        val result = defaultRepository.getBreedsByName("test")

        assert(result.status == Status.SUCCESS)
        assert(result.data?.size == 1)
    }

    @Test
    fun shouldFetchBreedsByNameAndReturnError() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = true
        remoteDataSource.shouldReturnEmptyValues = false

        val result = defaultRepository.getBreedsByName("test")

        assert(result.status == Status.ERROR)
    }

    @Test
    fun shouldFetchBreedsByNameAndReturnEmpty() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = true

        val result = defaultRepository.getBreedsByName("test")

        assert(result.status == Status.SUCCESS)
        assert(result.data?.size == 0)
    }

    @Test
    fun shouldFetchPhotoAndReturnError() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = true
        remoteDataSource.shouldReturnEmptyValues = false

        val result = defaultRepository.getBreedPhoto("12345")

        assert(result.status == Status.ERROR)
    }

    @Test
    fun shouldFetchPhotoAndReturnEmpty() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = true

        val result = defaultRepository.getBreedPhoto("12345")

        assert(result.status == Status.SUCCESS)
        assert(result.data?.size == 0)
    }

    @Test
    fun shouldFetchPhotoAndReturnOnePhoto() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = false

        val result = defaultRepository.getBreedPhoto("12345")

        assert(result.status == Status.SUCCESS)
        assert(result.data?.size == 1)
    }
}