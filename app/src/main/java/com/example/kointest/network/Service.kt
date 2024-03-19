package com.example.kointest

import com.example.kointest.data.Product
import retrofit2.Response
import retrofit2.http.GET


interface ProductService {
    @GET("products/1")
    suspend fun getProduct(): Response<Product>
}
