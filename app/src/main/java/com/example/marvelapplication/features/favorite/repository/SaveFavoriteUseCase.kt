package com.example.marvelapplication.features.favorite.repository

import com.example.marvelapplication.features.favorite.database.FavoriteDto

class SaveFavoriteUseCase(private val favoriteRepository: FavoriteRepository
) {
    /*suspend operator fun invoke(
        favorite: FavoriteDto
    ): Long {
        return favoriteRepository.save(favorite)
    }*/
}