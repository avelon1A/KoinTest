package com.example.kointest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kointest.databinding.ActivityMainBinding
import com.example.kointest.viewModel.ProductViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
 lateinit var binding: ActivityMainBinding

    private val viewModel: ProductViewModel by inject()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnData.setOnClickListener {
            viewModel.product.observe(this) { product ->
                product?.let {
                    binding.textTitle.text = it.title
                    binding.textDescription.text = it.description
                    binding.textPrice.text = "Price: $${it.price}"
                    binding.textRating.text = "Rating: ${it.rating}"
                    Log.d("data","$it")
                }
            }

        }
    }
}