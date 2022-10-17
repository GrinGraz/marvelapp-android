package cl.gringraz.marvelcatalog.feature.characterslist.data.repository

import arrow.core.Either
import cl.gringraz.corenetwork.ConnectionError
import cl.gringraz.corenetwork.UnknownError
import cl.gringraz.marvelcatalog.feature.characterslist.FakeDataFactory
import cl.gringraz.marvelcatalog.feature.characterslist.data.MarvelCharactersRepo
import cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote.MarvelCharactersRemoteSource
import cl.gringraz.marvelcatalog.feature.common.domain.characters.repository.MarvelCharactersRepository
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

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@MockKExtension.ConfirmVerification
@MockKExtension.CheckUnnecessaryStub
@DisplayName("Marvel Characters Repository Test")
class MarvelCharactersRepositoryTest {

    lateinit var testCoroutineDispatcher: TestDispatcher

    @MockK
    lateinit var dataSource: MarvelCharactersRemoteSource
    lateinit var sut: MarvelCharactersRepository

    @BeforeEach
    fun setUp() {
        testCoroutineDispatcher = StandardTestDispatcher()
        sut = spyk(MarvelCharactersRepo(dataSource), recordPrivateCalls = false)
    }

    @DisplayName("Given the request for marvel characters by the repository")
    @Nested
    inner class GetMarvelCharactersByRepo {

        @Nested
        @DisplayName("When the request and the mapping from remote response model to domain model is successful")
        inner class RequestAndMappingIsSuccessful {

            @BeforeEach
            fun before() {
                coEvery { dataSource.getMarvelCharacters() } returns Either.Right(FakeDataFactory.fakeMarvelCharactersResponseModel)
            }

            @Test
            @DisplayName("Then the repository gets a domain model of marvel characters")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacters()
                assertEquals(result, Either.Right(FakeDataFactory.fakeMarvelCharactersModel))
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { dataSource.getMarvelCharacters() }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
            }
        }

        @Nested
        @DisplayName("When the request fails with an unknown error")
        inner class RequestFailsWithUnknownError {

            @BeforeEach
            fun before() {
                coEvery { dataSource.getMarvelCharacters() } returns Either.Left(UnknownError)
            }

            @Test
            @DisplayName("Then the repository gets a marvel characters unknown domain error")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacters()
                assertEquals(result, Either.Left(FakeDataFactory.fakeUnknownMarvelError))
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { dataSource.getMarvelCharacters() }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
            }
        }

        @Nested
        @DisplayName("When the request fails with a connection error")
        inner class RequestFailsWithConnectionError {

            @BeforeEach
            fun before() {
                coEvery { dataSource.getMarvelCharacters() } returns Either.Left(ConnectionError)
            }

            @Test
            @DisplayName("Then the repository gets a marvel characters connection domain error")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacters()
                assertEquals(result, Either.Left(FakeDataFactory.fakeConnectionMarvelError))
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { dataSource.getMarvelCharacters() }
                coVerify(exactly = 1) { sut.getMarvelCharacters() }
            }
        }
    }
}
