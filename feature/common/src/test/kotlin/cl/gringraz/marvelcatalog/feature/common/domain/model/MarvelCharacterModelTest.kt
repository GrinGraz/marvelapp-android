package cl.gringraz.marvelcatalog.feature.common.domain.model

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.net.URL

@DisplayName("Domain Models Tests")
class MarvelCharacterModelTest {

    @Nested
    @DisplayName("Given it try to create a character model")
    inner class CreateMarvelCharacterThumbnail {

        @Nested
        @DisplayName("When character model thumbnail path and extensions are ok")
        inner class CreationOfAThumbnailIsSuccessful {

            @Test
            @DisplayName("Then it get a valid thumbnail url")
            fun `create character thumbnail with path and extension ok`() {
                val imageUrl = URL("https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg")
                val thumbnail = ModelFactory.createThumbnail(ModelFactory.ModelState.Ok)
                assertEquals(imageUrl.toString(), thumbnail.createThumbnailUrl())
            }
        }

        @Nested
        @DisplayName("When character model is created")
        inner class CreationMarvelCharacterIsSuccessful {

            @Test
            @DisplayName("Then it returns its description or default depends on its value")
            fun `create character thumbnail with path and extension ok`() {
                val modelWithDescription = MarvelCharacterModel(
                    id = 1L,
                    name = "name",
                    description = "description",
                    thumbnail = ModelFactory.createThumbnail(ModelFactory.ModelState.Ok)
                )

                assertEquals("description", modelWithDescription.getDescriptionOrDefault())

                val modelWithoutDescription = modelWithDescription.copy(description = null)

                assertEquals(
                    "We don't have the character's description right now.",
                    modelWithoutDescription.getDescriptionOrDefault()
                )
            }
        }
    }
}
