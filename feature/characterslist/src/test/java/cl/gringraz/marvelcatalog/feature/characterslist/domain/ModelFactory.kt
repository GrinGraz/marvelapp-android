package cl.gringraz.marvelcatalog.feature.characterslist.domain

import cl.gringraz.marvelcatalog.feature.characterslist.domain.model.MarvelCharacterModel
import cl.gringraz.marvelcatalog.feature.characterslist.domain.model.Thumbnail

object ModelFactory {

    enum class ModelState {
        Ok, Wrong
    }

    fun createMarvelCharacter(modelState: ModelState): MarvelCharacterModel = when(modelState){
        ModelState.Ok    -> {
            MarvelCharacterModel(
                thumbnail = createThumbnail(ModelState.Ok)
            )
        }
        ModelState.Wrong -> {
            MarvelCharacterModel(
                thumbnail = createThumbnail(ModelState.Wrong)
            )
        }
    }

    fun createThumbnail(modelState: ModelState): Thumbnail = when(modelState){
        ModelState.Ok    -> Thumbnail(path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", extension = "jpg")
        ModelState.Wrong -> Thumbnail(path = "http:/i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", extension = "jpg")
    }
}