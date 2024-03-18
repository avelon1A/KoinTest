package com.example.kointest.repo

import com.example.kointest.ProductService
import com.example.kointest.data.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val productService: ProductService) {
    fun getProduct(callback: (Product?, String?) -> Unit) {
        productService.getProduct().enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    callback(product, null)
                } else {
                    callback(null, "Failed to fetch product: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback(null, "Failed to fetch product: ${t.message}")
            }
        })
    }
}
