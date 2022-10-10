package cl.gringraz.marvelcatalog.feature.common.domain.model

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.Thumbnail

object ModelFactory {

    enum class ModelState {
        Ok, Wrong
    }

    fun createThumbnail(modelState: ModelState): Thumbnail = when (modelState) {
        ModelState.Ok -> Thumbnail(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
            extension = "jpg"
        )
        ModelState.Wrong -> Thumbnail(
            path = "http:/i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
            extension = "jpg"
        )
    }
}
