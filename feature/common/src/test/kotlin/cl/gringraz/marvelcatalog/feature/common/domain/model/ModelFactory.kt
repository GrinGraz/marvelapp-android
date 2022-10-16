package cl.gringraz.marvelcatalog.feature.common.domain.model

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ThumbnailModel

object ModelFactory {

    enum class ModelState {
        Ok, Wrong
    }

    fun createThumbnail(modelState: ModelState): ThumbnailModel = when (modelState) {
        ModelState.Ok -> ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
            extension = "jpg"
        )
        ModelState.Wrong -> ThumbnailModel(
            path = "http:/i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
            extension = "jpg"
        )
    }
}
