package cl.gringraz.feature.marvelcatalog.characterdetail.data.source

import arrow.core.Either
import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.ConnectionError
import cl.gringraz.corenetwork.UnknownError
import cl.gringraz.feature.marvelcatalog.characterdetail.data.FakeDataFactory
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.MarvelCharacterRemote
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelCharacterApi
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelCharacterRemoteSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@MockKExtension.ConfirmVerification
@MockKExtension.CheckUnnecessaryStub
@DisplayName("Marvel Characters Remote Source Test")
class MarvelCharacterRemoteSourceTest {

    lateinit var testCoroutineDispatcher: TestDispatcher

    @MockK
    lateinit var apiClient: ApiClient<MarvelCharacterApi>
    lateinit var sut: MarvelCharacterRemoteSource

    @BeforeEach
    fun setUp() {
        testCoroutineDispatcher = StandardTestDispatcher()
        sut = spyk(MarvelCharacterRemote(apiClient, testCoroutineDispatcher))
    }

    @Nested
    @DisplayName("Given the request for marvel character by the remote source")
    inner class GetMarvelCharactersByRemoteSource {

        @Nested
        @DisplayName("When the request to remote is successful")
        inner class RequestIsSuccessful {

            @BeforeEach
            fun before() {
                coEvery { apiClient.endpoints.getMarvelCharacterById("1") } returns FakeDataFactory.fakeQueriedMarvelCharactersResponse(
                    "1"
                )
            }

            @Test
            @DisplayName("Then the remote source gets a response model of marvel character")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacterById("1")
                assertEquals(
                    Either.Right(FakeDataFactory.fakeQueriedResponseModel("1")),
                    result
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacterById("1") }
                coVerify(exactly = 1) { sut.getMarvelCharacterById("1") }
            }
        }

        @Nested
        @DisplayName("When the request fails with an http error response")
        inner class RequestFailsWithUnknownError {

            @BeforeEach
            fun before() {
                coEvery { apiClient.endpoints.getMarvelCharacterById("1") } throws HttpException(
                    FakeDataFactory.errorResponse
                )
            }

            @Test
            @DisplayName("Then the remote source gets an unknown remote error")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacterById("1")
                assertEquals(Either.Left(UnknownError), result)
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacterById("1") }
                coVerify(exactly = 1) { sut.getMarvelCharacterById("1") }
            }
        }

        @Nested
        @DisplayName("When the request fails with a connection error")
        inner class RequestFailsWithConnectionError {

            @BeforeEach
            fun before() {
                coEvery { apiClient.endpoints.getMarvelCharacterById("1") } throws IOException()
            }

            @Test
            @DisplayName("Then the remote source gets a connection remote error")
            fun execute1() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacterById("1")
                assertEquals(Either.Left(ConnectionError), result)
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacterById("1") }
                coVerify(exactly = 1) { sut.getMarvelCharacterById("1") }
            }
        }

        // Add test for ApiError
    }
}
