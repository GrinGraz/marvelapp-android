package cl.gringraz.marvelcatalog.feature.characterslist.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

class MarvelCharactersDiffCallback : DiffUtil.ItemCallback<MarvelCharacterModel>() {
    override fun areItemsTheSame(
        oldItem: MarvelCharacterModel,
        newItem: MarvelCharacterModel,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MarvelCharacterModel,
        newItem: MarvelCharacterModel,
    ): Boolean {
        return oldItem == newItem
    }
}
