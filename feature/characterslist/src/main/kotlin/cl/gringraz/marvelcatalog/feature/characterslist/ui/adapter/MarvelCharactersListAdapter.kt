package cl.gringraz.marvelcatalog.feature.characterslist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.gringraz.marvelcatalog.feature.characterslist.databinding.MarvelCharacterItemBinding
import cl.gringraz.marvelcatalog.feature.characterslist.ui.throttledClickListener
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel

class MarvelCharactersListAdapter(
    private val itemClickListener: ItemClickListener,
    private val onLoadMoreItemsListener: LoadMoreItemsListener,
) : ListAdapter<MarvelCharacterModel, MarvelCharacterViewHolder>(MarvelCharactersDiffCallback()) {

    private var layoutManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null

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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutManager = recyclerView.layoutManager as? LinearLayoutManager?
        this.recyclerView = recyclerView
    }

    override fun submitList(list: MutableList<MarvelCharacterModel>?) {
        val newList = buildList {
            addAll(currentList)
            list?.let { addAll(it) }
        }
        super.submitList(newList)
    }

    fun addPagination() {
        fun isLastItemVisible() =
            layoutManager?.findLastCompletelyVisibleItemPosition() == itemCount - 1

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (isLastItemVisible()) onLoadMoreItemsListener.onItemsLoad()
            }
        })
    }

    fun submitSearch(characters: List<MarvelCharacterModel>) {
        super.submitList(characters.toMutableList())
    }

    fun interface ItemClickListener {
        fun onItemClick(item: MarvelCharacterModel)
    }

    fun interface LoadMoreItemsListener {
        fun onItemsLoad()
    }
}
