package cl.gringraz.marvelcatalog.feature.characterdetail.domain

import cl.gringraz.marvelcatalog.feature.common.domain.characters.model.ItemModel

data class CharacterDetailsModel(
    val sectionName: String,
    val sectionItems: List<ItemModel>
)
