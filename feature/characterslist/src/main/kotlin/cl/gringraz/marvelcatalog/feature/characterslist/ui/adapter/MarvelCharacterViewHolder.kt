package cl.gringraz.marvelcatalog.feature.characterslist.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import cl.gringraz.marvelcatalog.feature.characterslist.databinding.MarvelCharacterItemBinding
import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.MarvelCharacterModel
import com.squareup.picasso.Picasso

class MarvelCharacterViewHolder(private val binding: MarvelCharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(item: MarvelCharacterModel) {
        binding.characterName.text = item.name
        Picasso.get()
            .load(item.thumbnail.createThumbnailUrl())
            .fit()
            .centerCrop()
            .into(binding.characterImage)
        binding.like.setOnClickListener {
            binding.like.progress = 0F
            binding.like.pauseAnimation()
            binding.like.playAnimation()
        }
    }
}
