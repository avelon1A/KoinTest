package com.example.kointest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kointest.ApiClient
import com.example.kointest.ProductService

import com.example.kointest.data.Product
import com.example.kointest.repo.ProductRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    init {
        fetchProduct()
    }

    fun fetchProduct() {
        viewModelScope.launch {
            try {
                val response = productRepository.getproduct()
                if (response.isSuccessful) {
                    _product.postValue(response.body())
                } else {
                    println("Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }    fun getProducts(): LiveData<Product> {
        return product
    }
}


