package com.example.marvelapplication.features.character.repository

import com.example.marvelapplication.extensions.md5
import com.example.marvelapplication.features.character.model.MarvelCharacterList
import com.example.marvelapplication.features.character.network.ApiService
import com.example.marvelapplication.features.character.network.CharacterApiClient
import com.example.marvelapplication.features.character.network.Config.API_KEY
import com.example.marvelapplication.features.character.network.Config.PRIVATE_KEY

import retrofit2.Response
import java.util.*

class CharacterRepository {



    private var offset = 0
    lateinit var apiService: ApiService

     suspend fun getCharacter(
         apiKey: String,
         hash: String,
         ts: Long,
         offset: Int
     ) : Response<MarvelCharacterList>{

         apiService = CharacterApiClient.getCharacterApiClient().create(ApiService::class.java)
         return apiService.getMarvel(API_KEY,createTimestamp(),createHash(createTimestamp()),offset)
    }

    fun createTimestamp() = Date().time
    fun createHash(timestamp: Long) = (timestamp.toString() +PRIVATE_KEY + API_KEY).md5()


}