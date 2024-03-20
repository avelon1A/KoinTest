import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kointest.data.Product
import com.example.kointest.repo.ProductRepository
import com.example.kointest.viewModel.ProductViewModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
class ProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProductViewModel
    private lateinit var repository: ProductRepository
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mock(ProductRepository::class.java)
        viewModel = ProductViewModel(repository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
    @Test
    fun `fetchProduct should update LiveData with product data`() {
        // Mock data
        val product = Product(
            id = 1,
            title = "iPhone 9",
            description = "Detailed description",
            price = 19.99,
            discountPercentage = 0.0,
            rating = 4.2,
            stock = 10,
            brand = "Apple",
            category = "Smartphone",
            thumbnail = "thumbnail_url",
            images = listOf("image_url1", "image_url2")
        )
        val response = Response.success(product)
        runBlocking {
            whenever(repository.getproduct()).thenReturn(response)
        }
        viewModel.fetchProduct()
        val observedProduct = viewModel.product.value
        assert(observedProduct != null)
        assert(observedProduct!!.id == product.id)
        assert(observedProduct.title == product.title)
    }
}
