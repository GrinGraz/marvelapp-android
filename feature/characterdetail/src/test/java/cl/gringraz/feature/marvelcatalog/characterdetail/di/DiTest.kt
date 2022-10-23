package cl.gringraz.feature.marvelcatalog.characterdetail.di

import cl.gringraz.marvelcatalog.feature.characterdetail.di.characterDetailViewModel
import cl.gringraz.marvelcatalog.feature.characterdetail.presentation.MarvelCharacterDetailViewModel
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
    lateinit var viewModel: MarvelCharacterDetailViewModel

    @BeforeEach
    fun before() {
        testCoroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @Test
    @DisplayName("Dependencies are resolved ok")
    fun execute() = runTest(testCoroutineDispatcher) {
        coEvery { viewModel.getCharacterById("") } returns characterDetailViewModel().getCharacterById(
            ""
        )
        val result = viewModel.getCharacterById("")
        Assertions.assertEquals(Unit, result)
        coVerify { viewModel.getCharacterById("") }
    }
}
