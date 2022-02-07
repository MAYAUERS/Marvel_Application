package com.example.marvelapplication.features.favorite.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(FavoriteDto::class), version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun FavoriteDao():FavoriteDao

    companion object{

        @Volatile
        private var INSTANCE :FavoriteDatabase?=null

        fun getDatabase(context: Context):FavoriteDatabase{
            return INSTANCE?: synchronized(this){

                val intstance= Room.databaseBuilder(context.applicationContext,FavoriteDatabase::class.java,"fav_database").build()
                INSTANCE=intstance
                intstance
            }
        }

    }
}