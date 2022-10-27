package cl.gringraz.marvelcatalog.feature.characterdetail.data.repository

import arrow.core.Either
import cl.gringraz.corenetwork.ConnectionError
import cl.gringraz.corenetwork.UnknownError
import cl.gringraz.marvelcatalog.feature.characterdetail.data.FakeDataFactory
import cl.gringraz.marvelcatalog.feature.characterdetail.data.MarvelCharacterRepo
import cl.gringraz.marvelcatalog.feature.characterdetail.data.source.remote.MarvelCharacterRemoteSource
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.MarvelCharacterRepository
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
class MarvelCharacterRepositoryTest {

    lateinit var testCoroutineDispatcher: TestDispatcher

    @MockK
    lateinit var dataSource: MarvelCharacterRemoteSource
    lateinit var sut: MarvelCharacterRepository

    @BeforeEach
    fun setUp() {
        testCoroutineDispatcher = StandardTestDispatcher()
        sut = spyk(MarvelCharacterRepo(dataSource), recordPrivateCalls = false)
    }

    @DisplayName("Given the request for a marvel character by the repository")
    @Nested
    inner class GetMarvelCharactersByRepo {

        @Nested
        @DisplayName("When the request and the mapping from remote response model to domain model is successful")
        inner class RequestAndMappingIsSuccessful {

            @BeforeEach
            fun before() {
                coEvery { dataSource.getMarvelCharacterById("2") } returns Either.Right(
                    FakeDataFactory.fakeQueriedResponseModel("2")
                )
            }

            @Test
            @DisplayName("Then the repository gets a domain model of marvel characters")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacterById("2")
                assertEquals(
                    Either.Right(FakeDataFactory.fakeQueriedMarvelCharactersModel("2")),
                    result
                )
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { dataSource.getMarvelCharacterById("2") }
                coVerify(exactly = 1) { sut.getMarvelCharacterById("2") }
            }
        }

        @Nested
        @DisplayName("When the request fails with an unknown error")
        inner class RequestFailsWithUnknownError {

            @BeforeEach
            fun before() {
                coEvery { dataSource.getMarvelCharacterById("1") } returns Either.Left(UnknownError)
            }

            @Test
            @DisplayName("Then the repository gets a marvel characters unknown domain error")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacterById("1")
                assertEquals(result, Either.Left(FakeDataFactory.fakeUnknownMarvelError))
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { dataSource.getMarvelCharacterById("1") }
                coVerify(exactly = 1) { sut.getMarvelCharacterById("1") }
            }
        }

        @Nested
        @DisplayName("When the request fails with a connection error")
        inner class RequestFailsWithConnectionError {

            @BeforeEach
            fun before() {
                coEvery { dataSource.getMarvelCharacterById("1") } returns Either.Left(
                    ConnectionError
                )
            }

            @Test
            @DisplayName("Then the repository gets a marvel characters connection domain error")
            fun execute() = runTest(testCoroutineDispatcher) {
                val result = sut.getMarvelCharacterById("1")
                assertEquals(result, Either.Left(FakeDataFactory.fakeConnectionMarvelError))
            }

            @AfterEach
            fun after() {
                coVerify(exactly = 1) { dataSource.getMarvelCharacterById("1") }
                coVerify(exactly = 1) { sut.getMarvelCharacterById("1") }
            }
        }
    }
}
