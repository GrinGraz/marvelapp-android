package cl.gringraz.marvelcatalog.feature.common.domain.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.net.URL

@DisplayName("Domain Models Tests")
class MarvelCharacterModelTest {

    @Nested
    @DisplayName("Given it try to create thumbnail from character model")
    inner class CreateMarvelCharacterThumbnail {

        @Nested
        @DisplayName("When path and extensions are ok")
        inner class CreationIsSuccessful {

            @Test
            @DisplayName("Then it get a valid thumbnail url")
            fun `create character thumbnail with path and extension ok`() {
                val imageUrl = URL("https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg")
                val thumbnail = ModelFactory.createThumbnail(ModelFactory.ModelState.Ok)
                Assertions.assertEquals(imageUrl.toString(), thumbnail.createThumbnailUrl())
            }
        }
    }
}
