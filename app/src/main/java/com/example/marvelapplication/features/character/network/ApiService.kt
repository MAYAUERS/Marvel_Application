package com.example.marvelapplication.features.character.network

import com.example.marvelapplication.features.character.model.MarvelCharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("characters")
    suspend fun getMarvel(
        @Query("apikey") apiKey: String,
        @Query("ts") ts: Long,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): Response<MarvelCharacterList>

    @GET("characters")
    suspend fun searchCharacter(
        @Query("nameStartsWith") query: String,
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20
    ): Response<MarvelCharacterList>

    @GET("characters")
    suspend fun getAllCharacters(
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20
    ):  Response<MarvelCharacterList>


}