package com.example.kointest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ApiClientTest {

    @Test
    fun testBaseUrl() {
        assertEquals("https://dummyjson.com/", ApiClient.BASE_URL)
    }

    @Test
    fun testGsonConverterFactory() {
        val retrofit = ApiClient.create()
        assertNotNull(retrofit)

    }

    @Test
    fun testProductServiceCreation() {
        val productService = ApiClient.create()
        assertNotNull(productService)
    }

}
