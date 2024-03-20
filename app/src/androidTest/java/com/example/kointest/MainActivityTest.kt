import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import com.example.kointest.MainActivity
import com.example.kointest.ProductService
import com.example.kointest.data.Product
import com.example.kointest.viewModel.ProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Response
import kotlin.test.assertEquals

class MainActivityTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val  mockViewModel: ProductViewModel by inject()


    @Mock
    lateinit var mockapi: ProductService

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        mockapi = Mockito.mock(ProductService::class.java)
        mockViewModel.api = mockapi
    }
    @Test
    fun testButtonClickListener_ValidProduct() {
        val product = Product(
            1,
            "iPhone 9",
            "Detailed description",
            19.99,
            4.2,
            3.3,
            23,
            "brand",
            "phone",
            "url",
            listOf("url1", "url2")
        )
        val productLiveData = MutableLiveData<Product>().apply { value = product }
        runBlockingTest {
            `when`(mockapi.getProduct()).thenReturn(Response.success(product))
        }
        mockViewModel.fetchProduct()
        val result = mockViewModel.getProducts().getOrAwaitValueTest()
        assertEquals(result.description, product.description)
        activityScenario.onActivity { activity ->
            activity.binding.btnData.performClick()
            runBlocking {
                delay(1000)
            }
            assertEquals(activity.binding.textTitle.text.toString(), product.title)
        }
    }
    @Test
    fun btnClickTest(){
        activityScenario.onActivity { activity ->
          activity.binding.btnData.performClick()
            runBlocking {
                delay(1000)
            }
           assertEquals(activity.binding.textTitle.text.toString(),"iphone")
        }

    }
}
