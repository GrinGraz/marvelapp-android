package cl.gringraz.marvelcatalog.feature.characterdetail.ui.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import cl.gringraz.marvelcatalog.feature.characterdetail.databinding.MarvelCharacterDetailItemBinding
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ItemModel

class MarvelCharacterDetailAdapter :
    ListAdapter<ItemModel, MarvelCharacterDetailViewHolder>(MarvelCharacterDetailDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelCharacterDetailViewHolder {
        val binding =
            MarvelCharacterDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MarvelCharacterDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarvelCharacterDetailViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
}
