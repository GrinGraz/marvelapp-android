package cl.gringraz.marvelcatalog.feature.characterslist.presentation

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

internal object Navigation {

    fun Fragment.navigateToCharacterDetail(item: MarvelCharacterModel) {
        findNavController().navigate(createCharacterDetailDeeplink(item))
    }

    private fun createCharacterDetailDeeplink(item: MarvelCharacterModel): NavDeepLinkRequest {
        val uri = Uri.Builder()
            .scheme("android")
            .authority("marvel.app")
            .appendPath("character_detail")
            .appendPath(item.id.toString())
            .appendPath(item.name)
            .appendPath(item.getDescriptionOrDefault())
            .appendPath(item.thumbnail.createThumbnailUrl())
            .build()

        return NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
    }
}