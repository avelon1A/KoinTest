import com.example.kointest.ProductService
import com.example.kointest.data.Product
import com.example.kointest.repo.ProductRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepositoryTest {

    @Mock
    lateinit var productService: ProductService

    lateinit var productRepository: ProductRepository

    @Captor
    lateinit var callbackCaptor: ArgumentCaptor<Callback<Product>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        productRepository = ProductRepository(productService)
    }

    @Test
    fun `test getProduct success`() {
        val product = Product(
            id = 1,
            title = "Test Product",
            description = "Test Description",
            price = 99.99,
            discountPercentage = 0.0,
            rating = 4.5,
            stock = 10,
            brand = "Test Brand",
            category = "Test Category",
            thumbnail = "Test Thumbnail",
            images = listOf("Image 1", "Image 2")
        )

        val call = mock(Call::class.java) as Call<Product>
        `when`(productService.getProduct()).thenReturn(call)

        productRepository.getProduct { fetchedProduct, error ->
            assertEquals(product, fetchedProduct)
            assertEquals(null, error)
        }

        verify(call).enqueue(callbackCaptor.capture())
        callbackCaptor.value.onResponse(call, Response.success(product))
    }

    @Test
    fun `test getProduct failure`() {
        val errorMessage = "Failed to fetch product"
        val call = mock(Call::class.java) as Call<Product>
        `when`(productService.getProduct()).thenReturn(call)

        productRepository.getProduct { fetchedProduct, error ->
            assertEquals(null, fetchedProduct)
            assertTrue(error?.startsWith(errorMessage) ?: false)
        }

        verify(call).enqueue(callbackCaptor.capture())
        callbackCaptor.value.onFailure(call, Throwable(errorMessage))
    }

}
