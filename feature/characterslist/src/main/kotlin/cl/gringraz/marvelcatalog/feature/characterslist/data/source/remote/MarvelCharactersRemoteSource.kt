package cl.gringraz.marvelcatalog.feature.characterslist.data.source.remote

import okhttp3.Response

interface MarvelCharactersRemoteSource {
    fun getMarvelCharacters()
}
