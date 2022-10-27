package cl.gringraz.marvelcatalog.feature.characterdetail.presentation

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.characterdetail.data.FakeDataFactory
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.GetCharacterById
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@MockKExtension.ConfirmVerification
@MockKExtension.CheckUnnecessaryStub
@DisplayName("Marvel Characters View Model Test")
class CharactersViewModelTest {

    lateinit var testCoroutineDispatcher: TestDispatcher

    @MockK
    lateinit var getMarvelCharacterById: GetCharacterById

    @MockK
    lateinit var sut: MarvelCharacterDetailViewModel

    @BeforeEach
    fun setUp() {
        testCoroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
        sut =
            spyk(MarvelCharacterDetailViewModel(getMarvelCharacterById), recordPrivateCalls = false)
    }

    @AfterEach
    fun after() {
        clearAllMocks()
    }

    @DisplayName("Given the request for marvel characters by the view model")
    @Nested
    inner class GetMarvelCharactersByViewModel {

        @Nested
        @DisplayName("When the request and the mapping from use case is loading")
        inner class RequestAndMappingIsLoading {

            @BeforeEach
            fun before() {
                coEvery { getMarvelCharacterById("1") } returns Either.Right(
                    FakeDataFactory.fakeQueriedMarvelCharactersModel(
                        "1"
                    )
                )
            }

            @Test
            @DisplayName("Then the view model gets a marvel characters loading ui model")
            fun execute() = runTest(testCoroutineDispatcher) {
                sut.getCharacterById("1")
                assertEquals(MarvelCharacterDetailUiState.Loading, sut.marvelCharacterUiState.value)
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { getMarvelCharacterById("1") }
                coVerify(exactly = 1) { sut.getCharacterById("1") }
                coVerify(atMost = 2) { sut.marvelCharacterUiState }
            }
        }

        @Nested
        @DisplayName("When the request and the mapping from use case to ui model is successful")
        inner class RequestAndMappingIsSuccessful {

            @BeforeEach
            fun before() {
                coEvery { getMarvelCharacterById("1") } returns Either.Right(
                    FakeDataFactory.fakeQueriedMarvelCharactersModel(
                        "1"
                    )
                )
            }

            @Test
            @DisplayName("Then the view model gets a ui model of marvel characters")
            fun execute() = runTest(testCoroutineDispatcher) {
                sut.getCharacterById("1")
                delay(1)
                assertEquals(
                    MarvelCharacterDetailUiState.Success(FakeDataFactory.fakeCharacterDetailsModel),
                    sut.marvelCharacterUiState.value
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { getMarvelCharacterById("1") }
                coVerify(exactly = 1) { sut.getCharacterById("1") }
                coVerify(atMost = 2) { sut.marvelCharacterUiState }
            }
        }

        @Nested
        @DisplayName("When the request fails with an unknown error")
        inner class RequestFailsWithUnknownError {

            @BeforeEach
            fun before() {
                coEvery { getMarvelCharacterById("") } returns Either.Left(FakeDataFactory.fakeUnknownMarvelError)
            }

            @Test
            @DisplayName("Then the view model gets a marvel characters unknown ui error")
            fun execute() = runTest(testCoroutineDispatcher) {
                sut.getCharacterById("")
                delay(1)
                assertEquals(
                    MarvelCharacterDetailUiState.Error(FakeDataFactory.fakeUnknownMarvelError.message),
                    sut.marvelCharacterUiState.value
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { getMarvelCharacterById("") }
                coVerify(exactly = 1) { sut.getCharacterById("") }
                coVerify(atMost = 2) { sut.marvelCharacterUiState }
            }
        }

        @Nested
        @DisplayName("When the request fails with a connection error")
        inner class RequestFailsWithConnectionError {

            @BeforeEach
            fun before() {
                coEvery { getMarvelCharacterById("") } returns Either.Left(FakeDataFactory.fakeConnectionMarvelError)
            }

            @Test
            @DisplayName("Then the view model gets a marvel characters connection ui error")
            fun execute() = runTest(testCoroutineDispatcher) {
                sut.getCharacterById("")
                delay(1)
                assertEquals(
                    MarvelCharacterDetailUiState.Error(FakeDataFactory.fakeConnectionMarvelError.message),
                    sut.marvelCharacterUiState.value
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { getMarvelCharacterById("") }
                coVerify(exactly = 1) { sut.getCharacterById("") }
                coVerify(atMost = 2) { sut.marvelCharacterUiState }
            }
        }
    }
}
