package com.example.marvelapplication.features.character.network


import com.example.marvelapplication.features.character.network.Config.URL_BASE_MARVEL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object{


        private val httpLoggingInterceptor=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val okHttpClient=OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        fun getApiClient() :Retrofit{

            return Retrofit.Builder().baseUrl(URL_BASE_MARVEL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}