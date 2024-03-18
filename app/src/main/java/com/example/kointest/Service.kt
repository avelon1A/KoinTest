package com.example.kointest

import com.example.kointest.data.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("products/1")
    fun getProduct(): Call<Product>
}
