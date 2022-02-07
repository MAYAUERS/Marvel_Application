package com.example.marvelapplication.application

import android.app.Application
import com.example.marvelapplication.features.favorite.database.FavoriteDatabase
import com.example.marvelapplication.features.favorite.repository.FavoriteRepository

class MarvelApplication : Application() {

    private val database by lazy {

        FavoriteDatabase.getDatabase(this@MarvelApplication)
    }

    val repository by lazy {
        FavoriteRepository(database.FavoriteDao())
    }
}