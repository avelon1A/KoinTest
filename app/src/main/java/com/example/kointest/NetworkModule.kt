package com.example.kointest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module

val retrofitModule = module {
    single {
        val client = OkHttpClient.Builder().build()
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/") // Base URL for your API
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(ProductService::class.java) }
}
