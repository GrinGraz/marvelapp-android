package cl.gringraz.marvelcatalog.feature.characterslist.domain

import cl.gringraz.marvelcatalog.feature.characterslist.domain.usecase.GetMarvelCharacters
import cl.gringraz.marvelcatalog.feature.common.domain.characters.repository.MarvelCharactersRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Get Marvel Characters Test")
class GetMarvelCharactersTest {

    lateinit var repo: MarvelCharactersRepository
    lateinit var sut: GetMarvelCharacters

    @BeforeEach
    fun before() {
        repo = mockk()
        sut = GetMarvelCharacters { repo.getMarvelCharacters() }
        // coEvery { repo.getMarvelCharacters() } returns listOf()
    }

    @Test
    @DisplayName("Get marvel characters ok")
    fun getMarvelCharacter() = runTest {
        sut()
        coVerify { repo.getMarvelCharacters() }
    }
}
