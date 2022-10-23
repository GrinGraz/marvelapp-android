package cl.gringraz.marvelcatalog.feature.characterdetail.ui.adapter.details

import androidx.recyclerview.widget.DiffUtil
import cl.gringraz.marvelcatalog.feature.characterdetail.domain.CharacterDetailsModel

class MarvelCharacterDetailsDiffCallback : DiffUtil.ItemCallback<CharacterDetailsModel>() {
    override fun areItemsTheSame(
        oldItem: CharacterDetailsModel,
        newItem: CharacterDetailsModel
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(
        oldItem: CharacterDetailsModel,
        newItem: CharacterDetailsModel
    ): Boolean {
        return oldItem == newItem
    }
}
