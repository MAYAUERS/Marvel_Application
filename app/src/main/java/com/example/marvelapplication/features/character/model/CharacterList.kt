package com.example.marvelapplication.features.character.model

data class CharacterList(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: ArrayList<MarvelCharacters>

)
