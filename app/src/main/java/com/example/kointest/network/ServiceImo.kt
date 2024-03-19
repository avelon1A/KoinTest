package com.example.kointest.network

import com.example.kointest.ProductService
import com.example.kointest.data.Product
import retrofit2.Call
import retrofit2.Response

class ServiceImp(private val service: ProductService):ProductService {
    override suspend fun getProduct(): Response<Product> {
        return service.getProduct()
    }

}
