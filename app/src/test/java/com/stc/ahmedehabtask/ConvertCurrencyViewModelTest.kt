package com.stc.ahmedehabtask


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stc.ahmedehabtask.utilities.Constants
import com.stc.ahmedehabtask.viewModels.ConvertCurrencyViewModel
import com.stc.domain.entity.CurrenciesResponse
import com.stc.domain.usecase.GetLatest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ConvertCurrencyViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ConvertCurrencyViewModel

    @Mock
    private lateinit var getLatest: GetLatest

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ConvertCurrencyViewModel(getLatest)
    }

    @Test
    fun `test getLatest success`() = testScope.runBlockingTest {
        // Arrange
        val accessKey = Constants.accessKey
        val fakeResponse = CurrenciesResponse(
            base = "USD",
            date = "2023-08-28",
            rates = mapOf("USD" to 1.0, "EUR" to 0.85), // Adjust as needed
            success = true,
            timestamp = 1629964800
        )
        `when`(getLatest(accessKey)).thenReturn(fakeResponse)

        // Act
        viewModel.getLatest(accessKey)

        // Assert
        val response = viewModel.response.value
        assertEquals(fakeResponse, response)
        // You can also assert other ViewModel state changes if needed
    }

    @Test
    fun `test getLatest failure`() = testScope.runBlockingTest {
        // Arrange
        val accessKey = Constants.accessKey
        val expectedErrorMessage = "An error occurred"
        `when`(getLatest(accessKey)).thenThrow(RuntimeException(expectedErrorMessage))

        // Act
        viewModel.getLatest(accessKey)

        // Assert
        val actualErrorMessage = viewModel.errorMessage.value
        assertEquals(expectedErrorMessage, actualErrorMessage)
    }


    @Test
    fun `test getConvertedValue`() {
        // Arrange
        val valueText = "10"
        val selectedValueFrom = 2.0
        val selectedValueTo = 3.0
        val expectedResult = "15.0"

        // Act
        val result = viewModel.getConvertedValue(valueText, selectedValueFrom, selectedValueTo)

        // Assert
        assertEquals(expectedResult, result)
    }
}
