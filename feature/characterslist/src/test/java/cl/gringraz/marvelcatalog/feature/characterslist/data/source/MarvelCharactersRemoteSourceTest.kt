package cl.gringraz.marvelcatalog.feature.characterslist.data.source

import arrow.core.Either
import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.ConnectionError
import cl.gringraz.corenetwork.UnknownError
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelApi
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelCharactersRemoteSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.HttpException
import java.io.IOException

@DisplayName("Remote Data Source Test")
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@MockKExtension.ConfirmVerification
@MockKExtension.CheckUnnecessaryStub
class MarvelCharactersRemoteSourceTest {

    lateinit var testCoroutineDispatcher: TestDispatcher

    @MockK
    lateinit var apiClient: ApiClient<MarvelApi>
    lateinit var sut: MarvelCharactersRemoteSource

    @BeforeEach
    fun setUp() {
        testCoroutineDispatcher = StandardTestDispatcher()
        sut = spyk(MarvelCharactersRemote(apiClient, testCoroutineDispatcher))
    }

    @Nested
    @DisplayName("When I try to fetch marvel characters")
    inner class FetchPendingContracts {

        @Nested
        @DisplayName("If connection is successful")
        inner class ConnectionIsSuccessful {

            @BeforeEach
            fun before() {
                coEvery { apiClient.endpoints.getMarvelCharacters() } returns DataFactory.fakeMarvelCharactersResponse
            }

            @Test
            @DisplayName("Then I get a list")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacters()
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacters() }
                assertEquals(result, Either.Right(DataFactory.fakeMarvelCharactersResponseModel))
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
            }
        }

        @Nested
        @DisplayName("If there is a connection error")
        inner class ConnectionError {

            @Test
            @DisplayName("Then I get a Unknow RemoteError")
            fun execute() = runTest(testCoroutineDispatcher) {
                coEvery { apiClient.endpoints.getMarvelCharacters() } throws HttpException(DataFactory.errorResponse)
                val result = sut.getMarvelCharacters()
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacters() }
                assertEquals(result, Either.Left(UnknownError))
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
            }

            @Test
            @DisplayName("Then I get a Connetion RemoteError")
            fun execute1() = runTest(testCoroutineDispatcher) {
                coEvery { apiClient.endpoints.getMarvelCharacters() } throws IOException()
                val result = sut.getMarvelCharacters()
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacters() }
                assertEquals(result, Either.Left(ConnectionError))
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
            }

            // Add test for ApiError
        }
    }
}
