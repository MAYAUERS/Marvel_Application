package com.example.marvelapplication.features.favorite.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

   /* @Query("SELECT * FROM favorite")
    fun getAll(): List<FavoriteDto>

    @Query("SELECT * FROM favorite WHERE favoriteId = :favoriteId")
    fun get(favoriteId: Long): FavoriteDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(goalDataModel: FavoriteDto): Long

    @Delete
    fun delete(goalDataModel: FavoriteDto)*/


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(favoriteDto: FavoriteDto)

    @Delete
    fun delete(favoriteDto: FavoriteDto)

    @Query("Select * from favorite order by favoriteId ASC")
    fun getAllFav(): LiveData<List<FavoriteDto>>
}
