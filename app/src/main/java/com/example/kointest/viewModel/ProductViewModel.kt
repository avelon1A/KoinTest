package com.example.kointest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kointest.repo.ProductRepository
import com.example.kointest.data.Product

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    init {
        fetchProduct()
    }

    private fun fetchProduct() {
        productRepository.getProduct { product, error ->
            if (product != null) {
                _product.postValue(product)
            } else {
                // Handle error
                println("Error: $error")
            }
        }
    }
}
