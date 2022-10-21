package cl.gringraz.marvelcatalog.feature.characterslist.presentation

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.characterslist.FakeDataFactory
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.CharactersRequestQueryModel
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
    lateinit var getMarvelCharacters: GetMarvelCharacters

    @MockK
    lateinit var sut: MarvelCharactersViewModel

    @BeforeEach
    fun setUp() {
        testCoroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
        sut = spyk(MarvelCharactersViewModel(getMarvelCharacters), recordPrivateCalls = false)
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
                coEvery { getMarvelCharacters(null) } returns Either.Right(FakeDataFactory.fakeMarvelCharactersModel)
            }

            @Test
            @DisplayName("Then the view model gets a marvel characters loading ui model")
            fun execute() = runTest(testCoroutineDispatcher) {
                sut.getMarvelCharacters()
                assertEquals(MarvelCharactersListUiState.Loading, sut.marvelCharactersUiState.value)
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { getMarvelCharacters(null) }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
                coVerify(atMost = 2) { sut.marvelCharactersUiState }
            }
        }

        @Nested
        @DisplayName("When the request and the mapping from use case to ui model is successful")
        inner class RequestAndMappingIsSuccessful {

            @BeforeEach
            fun before() {
                coEvery { getMarvelCharacters(null) } returns Either.Right(FakeDataFactory.fakeMarvelCharactersModel)
            }

            @Test
            @DisplayName("Then the view model gets a ui model of marvel characters")
            fun execute() = runTest(testCoroutineDispatcher) {
                sut.getMarvelCharacters()
                delay(1)
                assertEquals(
                    MarvelCharactersListUiState.Success(FakeDataFactory.fakeMarvelCharactersModel),
                    sut.marvelCharactersUiState.value
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { getMarvelCharacters(null) }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
                coVerify(atMost = 2) { sut.marvelCharactersUiState }
            }
        }

        @Nested
        @DisplayName("When the request with params and the mapping from use case to ui model is successful")
        inner class RequestWithParamsAndMappingIsSuccessful {

            private lateinit var requestQueryModel: CharactersRequestQueryModel

            @BeforeEach
            fun before() {
                requestQueryModel =
                    FakeDataFactory.fakeCharactersRequestQueryModel
                coEvery { getMarvelCharacters(requestQueryModel) } returns Either.Right(
                    FakeDataFactory.fakeQueriedMarvelCharactersModel(requestQueryModel.nameStartsWith!!)
                )
            }

            @Test
            @DisplayName("Then the view model gets a ui model of marvel characters")
            fun execute() = runTest(testCoroutineDispatcher) {
                sut.getMarvelCharacters(requestQueryModel)
                delay(1)
                assertEquals(
                    MarvelCharactersListUiState.Success(
                        FakeDataFactory.fakeQueriedMarvelCharactersModel(
                            "second"
                        )
                    ),
                    sut.marvelCharactersUiState.value
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { getMarvelCharacters(requestQueryModel) }
                coVerify(exactly = 1) { sut.getMarvelCharacters(requestQueryModel) }
                coVerify(atMost = 2) { sut.marvelCharactersUiState }
            }
        }

        @Nested
        @DisplayName("When the request fails with an unknown error")
        inner class RequestFailsWithUnknownError {

            @BeforeEach
            fun before() {
                coEvery { getMarvelCharacters(null) } returns Either.Left(FakeDataFactory.fakeUnknownMarvelError)
            }

            @Test
            @DisplayName("Then the view model gets a marvel characters unknown ui error")
            fun execute() = runTest(testCoroutineDispatcher) {
                sut.getMarvelCharacters()
                delay(1)
                assertEquals(
                    MarvelCharactersListUiState.Error(FakeDataFactory.fakeUnknownMarvelError.message),
                    sut.marvelCharactersUiState.value
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { getMarvelCharacters(null) }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
                coVerify(atMost = 2) { sut.marvelCharactersUiState }
            }
        }

        @Nested
        @DisplayName("When the request fails with a connection error")
        inner class RequestFailsWithConnectionError {

            @BeforeEach
            fun before() {
                coEvery { getMarvelCharacters(null) } returns Either.Left(FakeDataFactory.fakeConnectionMarvelError)
            }

            @Test
            @DisplayName("Then the view model gets a marvel characters connection ui error")
            fun execute() = runTest(testCoroutineDispatcher) {
                sut.getMarvelCharacters()
                delay(1)
                assertEquals(
                    MarvelCharactersListUiState.Error(FakeDataFactory.fakeConnectionMarvelError.message),
                    sut.marvelCharactersUiState.value
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { getMarvelCharacters(null) }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
                coVerify(atMost = 2) { sut.marvelCharactersUiState }
            }
        }
    }
}
