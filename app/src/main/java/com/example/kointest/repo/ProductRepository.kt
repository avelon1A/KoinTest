package com.example.kointest.repo

import com.example.kointest.ProductService
import com.example.kointest.data.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val productService: ProductService) {
suspend fun getproduct(): Response<Product> {
    return productService.getProduct()

}
}
