package cl.gringraz.marvelcatalog.feature.characterslist.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import cl.gringraz.marvelcatalog.feature.characterslist.R
import cl.gringraz.marvelcatalog.feature.characterslist.databinding.MarvelCharacterItemBinding
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import com.squareup.picasso.Picasso

class MarvelCharacterViewHolder(private val binding: MarvelCharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(item: MarvelCharacterModel) {
        with(binding) {
            characterName.text = item.name
            Picasso.get()
                .load(item.thumbnail.createThumbnailUrl())
                .error(R.drawable.ic_broken_image_24)
                .fit()
                .centerCrop()
                .into(characterImage)
            like.setOnClickListener {
                like.progress = 0F
                like.pauseAnimation()
                like.playAnimation()
            }
        }
    }
}
