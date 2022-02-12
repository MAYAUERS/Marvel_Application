package com.example.marvelapplication.features.character.repository

import com.example.marvelapplication.features.character.model.MarvelCharacterList
import com.example.marvelapplication.features.character.network.ApiService
import com.example.marvelapplication.features.character.network.ApiClient
import retrofit2.Response


class CharacterRepository {


    private lateinit var apiService: ApiService

     suspend fun getCharacter(
         apiKey: String,
         hash: String,
         ts: Long,
         offset: Int
     ) : Response<MarvelCharacterList>{

         apiService = ApiClient.getApiClient().create(ApiService::class.java)
         return apiService.getMarvel(apiKey,ts,hash,offset)
    }


}
