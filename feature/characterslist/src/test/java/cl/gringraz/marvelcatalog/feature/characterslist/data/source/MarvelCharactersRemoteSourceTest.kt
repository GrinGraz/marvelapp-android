package cl.gringraz.marvelcatalog.feature.characterslist.data.source

import arrow.core.Either
import cl.gringraz.corenetwork.ApiClient
import cl.gringraz.corenetwork.ConnectionError
import cl.gringraz.corenetwork.UnknownError
import cl.gringraz.marvelcatalog.feature.characterslist.FakeDataFactory
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelApi
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelCharactersRemoteSource
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
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
    @DisplayName("Given the request for marvel characters by the remote source")
    inner class GetMarvelCharactersByRemoteSource {

        @Nested
        @DisplayName("When the request to remote is successful")
        inner class RequestIsSuccessful {

            @BeforeEach
            fun before() {
                coEvery { apiClient.endpoints.getMarvelCharacters() } returns FakeDataFactory.fakeMarvelCharactersResponse
            }

            @Test
            @DisplayName("Then the remote source gets a response model of marvel characters")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacters()
                assertEquals(
                    Either.Right(FakeDataFactory.fakeMarvelCharactersResponseModel),
                    result
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacters() }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
            }
        }

        @Nested
        @DisplayName("When the request with params to remote is successful")
        inner class RequestWithParamsIsSuccessful {

            private lateinit var requestQueryModel: CharactersRequestQueryModel
            private lateinit var queryMap: Map<String, String>

            @BeforeEach
            fun before() {
                requestQueryModel = FakeDataFactory.fakeCharactersRequestQueryModel
                queryMap = FakeDataFactory.fakeQueryMapFrom(requestQueryModel)
                coEvery { apiClient.endpoints.getMarvelCharacters(queryMap) } returns FakeDataFactory.fakeQueriedMarvelCharactersResponse(
                    requestQueryModel.nameStartsWith!!
                )
            }

            @Test
            @DisplayName("Then the remote source gets a queried response model of marvel characters")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacters(requestQueryModel)
                assertEquals(
                    Either.Right(FakeDataFactory.fakeQueriedResponseModel("second")),
                    result
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacters(queryMap) }
                coVerify(exactly = 1) { sut.getMarvelCharacters(requestQueryModel) }
            }
        }

        @Nested
        @DisplayName("When the request fails with an http error response")
        inner class RequestFailsWithUnknownError {

            @BeforeEach
            fun before() {
                coEvery { apiClient.endpoints.getMarvelCharacters() } throws HttpException(
                    FakeDataFactory.errorResponse
                )
            }

            @Test
            @DisplayName("Then the remote source gets an unknown remote error")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacters()
                assertEquals(Either.Left(UnknownError), result)
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacters() }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
            }
        }

        @Nested
        @DisplayName("When the request fails with a connection error")
        inner class RequestFailsWithConnectionError {

            @BeforeEach
            fun before() {
                coEvery { apiClient.endpoints.getMarvelCharacters() } throws IOException()
            }

            @Test
            @DisplayName("Then the remote source gets a connection remote error")
            fun execute1() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacters()
                assertEquals(Either.Left(ConnectionError), result)
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { apiClient.endpoints.getMarvelCharacters() }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
            }
        }

        // Add test for ApiError
    }
}
