package com.example.marvelapplication.features.favorite.repository

interface ListModeRepository {

    suspend fun saveListMode(listMode: Boolean)

    suspend fun getListMode(): Boolean
}
