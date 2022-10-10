package cl.gringraz.marvelcatalog.feature.characterslist.domain

import org.junit.jupiter.api.*

@DisplayName("Domain Models Tests")
class MarvelCharacterModelTests {

    @Nested
    @DisplayName("When it try to create thumbnail from character model")
    inner class CreateMarvelCharacterThumbnail {

        @Nested
        @DisplayName("If path and extensions are ok")
        inner class RequestIsSuccessful {

            @Test
            @DisplayName("Then it get a valid thumbnail url")
            fun `create character thumbnail with path and extension ok`() {
                val imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
                val thumbnail = ModelFactory.createThumbnail(ModelFactory.ModelState.Ok)
                Assertions.assertEquals(imageUrl, thumbnail.createImageUrl())
            }
        }
    }
}