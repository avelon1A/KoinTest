import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.kointest.MainActivity
import com.example.kointest.ProductService
import com.example.kointest.R
import com.example.kointest.data.Product
import com.example.kointest.repo.ProductRepository
import com.example.kointest.viewModel.ProductViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java, true, false)

    private val appModule = module {
        val repo = ProductRepository(InMemoryLoginApi())
        viewModel { ProductViewModel(repo) }
    }

    @Before
    fun setUp() {
        loadKoinModules(appModule)
        rule.launchActivity(Intent())
    }

    @Test
    fun should_display_correct_product_data_when_button_clicked() {
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
        onView(withId(R.id.btn_data)).perform(click())
        runBlocking { delay(2000) }
        onView(withId(R.id.textTitle)).check(matches(withText(product.title)))
        onView(withId(R.id.textDescription)).check(matches(withText(product.description)))
        onView(withId(R.id.textPrice)).check(matches(withText("Price: $19.99")))
        onView(withId(R.id.textRating)).check(matches(withText("Rating: 4.2")))
    }

    @After
    fun tearDown() {
        unloadKoinModules(appModule)
    }

    inner class InMemoryLoginApi : ProductService {
        override suspend fun getProduct(): Response<Product> {
            return Response.success(
                Product(
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
            )
        }
    }
}
