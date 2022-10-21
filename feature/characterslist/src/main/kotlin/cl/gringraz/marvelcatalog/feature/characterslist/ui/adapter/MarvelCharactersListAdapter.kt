package cl.gringraz.marvelcatalog.feature.characterslist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import cl.gringraz.marvelcatalog.feature.characterslist.databinding.MarvelCharacterItemBinding
import cl.gringraz.marvelcatalog.feature.characterslist.ui.throttledClickListener
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

class MarvelCharactersListAdapter(
    private val itemClickListener: ItemClickListener,
) : ListAdapter<MarvelCharacterModel, MarvelCharacterViewHolder>(MarvelCharactersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharacterViewHolder {
        val binding =
            MarvelCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarvelCharacterViewHolder(binding).also { holder ->
            with(binding.root) {
                throttledClickListener {
                    itemClickListener.onItemClick(
                        item = currentList[holder.absoluteAdapterPosition]
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MarvelCharacterViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    fun interface ItemClickListener {
        fun onItemClick(item: MarvelCharacterModel)
    }
}
