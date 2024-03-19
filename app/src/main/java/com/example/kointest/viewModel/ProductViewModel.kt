package com.example.kointest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kointest.repo.ProductRepository
import com.example.kointest.data.Product
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    init {
        fetchProduct()
    }

    fun fetchProduct() {
        GlobalScope.launch {
            val response = productRepository.getproduct()
            if (response.isSuccessful) {
                _product.postValue(response.body())
            } else {
                // Handle error
                println("Error: ${response.errorBody()}")
            }
        }
    }
}


