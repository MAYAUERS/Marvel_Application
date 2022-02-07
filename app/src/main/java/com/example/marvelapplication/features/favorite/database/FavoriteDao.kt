package com.example.marvelapplication.features.favorite.database

import androidx.room.Delete
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {

    /*@Insert
    suspend fun insertFav(favoriteDto: FavoriteDto)*/

    @Delete
    fun delete(favoriteDto: FavoriteDto)


    @Query("SELECT * FROM favorite ORDER BY favoriteId")
    fun getAllMarvelList(): Flow<List<FavoriteDto>>


}
