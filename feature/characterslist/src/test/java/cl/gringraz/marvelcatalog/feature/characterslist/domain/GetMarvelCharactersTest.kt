package cl.gringraz.marvelcatalog.feature.characterslist.domain

import arrow.core.Either
import cl.gringraz.marvelcatalog.feature.characterslist.FakeDataFactory
import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharactersError
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
@DisplayName("Get Marvel Characters Test")
class GetMarvelCharactersTest {

    lateinit var testCoroutineDispatcher: TestDispatcher

    @MockK
    lateinit var repo: MarvelCharactersRepository
    lateinit var sut: GetMarvelCharacters

    @BeforeEach
    fun before() {
        testCoroutineDispatcher = StandardTestDispatcher()
        sut = spyk(GetMarvelCharacters { repo.getMarvelCharacters() })
    }

    @Nested
    @DisplayName("Given the execution for marvel characters by the use case")
    inner class GetMarvelCharactersByUseCase {

        @Nested
        @DisplayName("When the request to the repo is successful")
        inner class GetMarvelCharactersIsSuccessful {

            @BeforeEach
            fun before() {
                coEvery { repo.getMarvelCharacters() } returns Either.Right(FakeDataFactory.fakeMarvelCharactersModel)
            }

            @Test
            @DisplayName("Then the use case gets a domain model of marvel characters")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut(null)
                assertEquals(Either.Right(FakeDataFactory.fakeMarvelCharactersModel), result)
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { repo.getMarvelCharacters() }
                coVerify(exactly = 1) { sut(null) }
            }
        }

        @Nested
        @DisplayName("When the use case fails with an unknown error response")
        inner class GetMarvelCharactersFailsWithUnknownError {

            @BeforeEach
            fun before() {
                coEvery { repo.getMarvelCharacters() } returns Either.Left(FakeDataFactory.fakeUnknownMarvelError)
            }

            @Test
            @DisplayName("Then the use case gets an unknown domain error")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut(null)
                assertEquals(Either.Left(MarvelCharactersError("Unknown error")), result)
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { repo.getMarvelCharacters() }
                coVerify(exactly = 1) { sut(null) }
            }
        }

        @Nested
        @DisplayName("When the use case fails with a connection error")
        inner class GetMarvelCharactersFailsWithConnectionError {

            @BeforeEach
            fun before() {
                coEvery { repo.getMarvelCharacters() } returns Either.Left(FakeDataFactory.fakeConnectionMarvelError)
            }

            @Test
            @DisplayName("Then the use case gets a connection domain error")
            fun execute1() = runTest(testCoroutineDispatcher) {
                val result = sut(null)
                assertEquals(Either.Left(MarvelCharactersError("Connection error")), result)
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { repo.getMarvelCharacters() }
                coVerify(exactly = 1) { sut(null) }
            }
        }
    }
}
