package com.example.marvelapplication.features.favorite.repository

import com.example.marvelapplication.features.favorite.database.FavoriteDao
import com.example.marvelapplication.features.favorite.database.FavoriteDto
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun deleteFave(favoriteDto: FavoriteDto){
        favoriteDao.delete(favoriteDto)
    }

  /*  suspend fun insertFavMarvel(favoriteDto: FavoriteDto){
        favoriteDao.insertFav(favoriteDto)
    }*/

    val allMarvelList : Flow<List<FavoriteDto>> =favoriteDao.getAllMarvelList()

}


