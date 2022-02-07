package com.rafaelfelipeac.marvelapp.features.details.domain.model

data class CharacterDetailList(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterDetail>
)
