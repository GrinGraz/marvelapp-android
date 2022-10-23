package cl.gringraz.marvelcatalog.feature.characterdetail.domain

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ThumbnailModel

data class MarvelComicModel(
    val thumbnailModel: ThumbnailModel
) {
    companion object {
        val empty by lazy { MarvelComicModel(ThumbnailModel("", "")) }
    }
}
