package com.example.marvelapplication.features.favorite.repository

import androidx.lifecycle.LiveData
import com.example.marvelapplication.features.favorite.database.FavoriteDao
import com.example.marvelapplication.features.favorite.database.FavoriteDto
import com.example.marvelapplication.features.favorite.model.Favorite

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val allFavorite:LiveData<List<FavoriteDto>> =favoriteDao.getAllFav()

    fun insertFav(favoriteDto: FavoriteDto){
        favoriteDao.save(favoriteDto)
    }

    fun deleteFave(favoriteDto: FavoriteDto){
        favoriteDao.delete(favoriteDto)
    }

}