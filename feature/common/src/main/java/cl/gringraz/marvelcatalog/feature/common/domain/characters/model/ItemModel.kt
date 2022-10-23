package cl.gringraz.marvelcatalog.feature.common.domain.characters.model

typealias Comic = ItemModel
typealias Story = ItemModel
typealias Serie = ItemModel
typealias Event = ItemModel

data class ItemModel(
    val resourceURI: String,
    val name: String
)
