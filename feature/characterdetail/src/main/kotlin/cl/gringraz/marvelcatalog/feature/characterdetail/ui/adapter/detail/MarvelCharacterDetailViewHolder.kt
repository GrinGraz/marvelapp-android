package cl.gringraz.marvelcatalog.feature.characterdetail.ui.adapter.detail

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cl.gringraz.marvelcatalog.feature.characterdetail.databinding.MarvelCharacterDetailItemBinding
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ItemModel

class MarvelCharacterDetailViewHolder(private val binding: MarvelCharacterDetailItemBinding) :
    ViewHolder(binding.root) {
    fun bindTo(item: ItemModel) {
        with(binding) {
            itemName.text = item.name
        }
    }
}
