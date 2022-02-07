package com.rafaelfelipeac.marvelapp.features.details.domain.model

data class CharacterDetail(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)
