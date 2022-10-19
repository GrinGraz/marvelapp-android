package cl.gringraz.marvelcatalog.feature.characterslist.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import cl.gringraz.marvelcatalog.feature.characterslist.databinding.MarvelCharacterItemBinding
import cl.gringraz.marvelcatalog.feature.characterslist.presentation.throttledClickListener
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

class MarvelCharactersListAdapter(private val itemClickListener: ItemClickListener) :
    ListAdapter<MarvelCharacterModel, MarvelCharacterViewHolder>(MarvelCharactersDiffCallback()),
    Filterable {

    val originalList: List<MarvelCharacterModel> by lazy { currentList }

    fun interface ItemClickListener {
        fun onItemClick(item: MarvelCharacterModel)
    }

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

    override fun getFilter(): Filter {
        var filteredList = originalList
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    submitList(originalList)
                } else {
                    filteredList = originalList.filter {
                        it.name.contains(other = charString, ignoreCase = true)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults,
            ) {
                val castedResults = try {
                    filterResults.values as MutableList<*>
                } catch (e: Exception) {
                    originalList
                }

                val resolvedList = castedResults.filterIsInstance<MarvelCharacterModel>()
                filteredList = resolvedList

                submitList(filteredList)
            }
        }
    }
}
