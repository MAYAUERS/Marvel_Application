package com.example.marvelapplication.features.details.network

import com.rafaelfelipeac.marvelapp.features.details.domain.model.CharacterDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {

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
    ): Response<CharacterDetail>

    @GET("characters/{characterId}/series")
    suspend fun getDetailsSeries(
        @Path("characterId") characterId: Long,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long,
        @Query("offset") offset: Int
    ): Response<CharacterDetail>
}