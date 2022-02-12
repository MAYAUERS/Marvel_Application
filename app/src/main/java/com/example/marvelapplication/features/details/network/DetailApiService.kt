package com.example.marvelapplication.features.details.network

import com.example.marvelapplication.features.details.model.CharacterDetail
import com.example.marvelapplication.features.details.model.MarvelCharacterDetail
import com.example.marvelapplication.features.details.model.MarvelDetailInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApiService{

    @GET("characters/{characterId}")
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: Long,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long
    ): Response<MarvelCharacterDetail>

    @GET("characters/{characterId}")
    suspend fun getDetails(
        @Path("characterId") characterId: Long,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long
    ): Response<CharacterDetail>

    @GET("characters/{characterId}/comics")
    suspend fun getDetailsComics(
        @Path("characterId") characterId: Long,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long,
        @Query("offset") offset: Int
    ): Response<MarvelDetailInfo>

    @GET("characters/{characterId}/series")
    suspend fun getDetailsSeries(
        @Path("characterId") characterId: Long,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long,
        @Query("offset") offset: Int
    ): Response<MarvelDetailInfo>
//    https://gateway.marvel.com/v1/public/characters/1017100/comics?apikey=97306494848962a14b2b359525f43537&hash=6c723e3da56fbe3e1075c13e9e0769bc&ts=1644289332871
}