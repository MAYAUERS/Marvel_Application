package com.example.marvelapplication.features.favorite.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteDto(
    @PrimaryKey(autoGenerate = true)
    val favoriteId: Long,
    var favoriteName: String,
    var favoriteUrl: String
)