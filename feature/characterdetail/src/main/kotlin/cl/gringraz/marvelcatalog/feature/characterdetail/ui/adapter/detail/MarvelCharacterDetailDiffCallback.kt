package cl.gringraz.marvelcatalog.feature.characterdetail.ui.adapter.detail

import androidx.recyclerview.widget.DiffUtil
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ItemModel

class MarvelCharacterDetailDiffCallback : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem.name == newItem.name
    }
}
