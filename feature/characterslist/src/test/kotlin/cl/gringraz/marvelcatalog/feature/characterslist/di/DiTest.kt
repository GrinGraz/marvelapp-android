package cl.gringraz.marvelcatalog.feature.characterslist.di

import cl.gringraz.marvelcatalog.feature.characterslist.presentation.MarvelCharactersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@MockKExtension.ConfirmVerification
@MockKExtension.CheckUnnecessaryStub
@DisplayName("Di Test")
class DiTest {

    private lateinit var testCoroutineDispatcher: TestDispatcher

    @MockK
    lateinit var viewModel: MarvelCharactersViewModel

    @BeforeEach
    fun before() {
        testCoroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @Test
    @DisplayName("Dependencies are resolved ok")
    fun execute() = runTest(testCoroutineDispatcher) {
        coEvery { viewModel.getMarvelCharacters() } returns charactersViewModel().getMarvelCharacters()
        val result = viewModel.getMarvelCharacters()
        Assertions.assertEquals(Unit, result)
        coVerify { viewModel.getMarvelCharacters() }
    }
}
