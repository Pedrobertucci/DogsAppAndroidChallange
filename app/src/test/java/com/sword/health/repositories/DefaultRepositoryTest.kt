package com.sword.health.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sword.health.remote.FakeRemoteDataSourceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var defaultRepository: DefaultRepository

    private var remoteDataSource = FakeRemoteDataSourceTest()

    @Before
    fun setUp() {
        defaultRepository = DefaultRepository(remoteDataSource)
    }

    @Test
    fun `should fetch breeds and return a error`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = true

        val result = remoteDataSource.getBreeds(apiKey = "", page = 1, limit = 20)

        assert(!result.isSuccessful)
    }

    @Test
    fun `should fetch breeds and return a breed`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = false

        val result = remoteDataSource.getBreeds(apiKey = "", page = 1, limit = 20)

        assert(result.isSuccessful)
        assert(result.body()?.isNotEmpty() ?: false)
    }

    @Test
    fun `should fetch breeds and return empty`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = true

        val result = remoteDataSource.getBreeds(apiKey = "", page =1, limit = 20)

        assert(result.isSuccessful)
        assert(result.body()?.isEmpty() ?: false)
    }

    @Test
    fun `should fetch breeds by name and return a breed`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = false

        val result = remoteDataSource.getBreedsByName(apiKey = "", query = "test")

        assert(result.isSuccessful)
        assert(result.body()?.isNotEmpty() ?: false)
    }

    @Test
    fun `should fetch breeds by name and return a error`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = true

        val result = remoteDataSource.getBreedsByName(apiKey = "", query = "test")

        assert(!result.isSuccessful)
    }

    @Test
    fun `should fetch breeds by name repository and return empty`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = true

        val result = remoteDataSource.getBreedsByName(apiKey = "", query = "test")

        assert(result.isSuccessful)
        assert(result.body()?.isEmpty() ?: false)
    }

    @Test
    fun `should fetch photo and return a error`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = true
        remoteDataSource.shouldReturnEmptyValues = false


        val result = remoteDataSource.getBreedPhoto(apiKey = "", size = "small",
                                                   format = "json", breedId = "123456")

        assert(!result.isSuccessful)
    }

    @Test
    fun `should fetch photo and return empty`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = true

        val result = remoteDataSource.getBreedPhoto(apiKey = "", size = "small",
                                                   format = "json", breedId = "123456")

        assert(result.isSuccessful)
        assert(result.body()?.isEmpty() ?: false)
    }

    @Test
    fun `should fetch photo and return a photo`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = false

        val result = remoteDataSource.getBreedPhoto(apiKey = "", size = "small",
            format = "json", breedId = "123456")

        assert(result.isSuccessful)
        assert(result.body()?.isNotEmpty() ?: false)
    }

}