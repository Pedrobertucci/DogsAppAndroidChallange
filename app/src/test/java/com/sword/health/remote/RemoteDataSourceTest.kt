package com.sword.health.remote

import com.sword.health.utils.FilePath
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSourceTest {
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val request = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RemoteContract::class.java)

    @Test
    fun `should fetch breeds with page 1 and limit 20`() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                FilePath("getBreadsPage1Limit20Code200.json").getContent()
            ).setResponseCode(200))

        runBlocking {
            val response = request.getBreeds(
                page = 1,
                limit = 20)

            assertEquals(response.body()?.size, 20)
        }
    }

    @Test
    fun `should fetch breeds with page 2 and limit 20`() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                FilePath("getBreadsPage1Limit20Code200.json").getContent()
            ).setResponseCode(200))

        runBlocking {
            val response = request.getBreeds(page = 2,
                                             limit = 20)

            assertEquals(response.body()?.size, 20)
        }
    }

    @Test
    fun `should fetch breeds with name test and result query is empty`() {
        mockWebServer.enqueue(
            MockResponse().setBody("[]").setResponseCode(200))

        runBlocking {
            val response = request.getBreedsByName(query = "test")

            assertEquals(response.body()?.size, 0)
        }
    }

    @Test
    fun `should fetch breeds with name Small and result one breed`() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                FilePath("getBreadQuerySmall200.json").getContent()
            ).setResponseCode(200))

        runBlocking {
            val response = request.getBreedsByName(query = "Small")

            assertEquals(response.body()?.size, 1)
        }
    }

    @Test
    fun `should fetch breed photo`() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                FilePath("getBreadPhoto200.json").getContent()
            ).setResponseCode(200))

        runBlocking {
            val response = request.getBreedPhoto(size = "med",
                                                 format = "json",
                                                 breedId = "68")

            assert(!response.body().isNullOrEmpty())
            assertEquals(response.body()?.size, 1)
            assert(!response.body()?.get(0)?.url.isNullOrEmpty())
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}