package com.stc.ahmedehabtask.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stc.domain.entity.CurrenciesResponse
import com.stc.domain.usecase.GetLatest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val getPostsUseCase: GetLatest
) : ViewModel() {
    private val _response: MutableStateFlow<CurrenciesResponse?> = MutableStateFlow(null)
    val response: StateFlow<CurrenciesResponse?> = _response
    var errorMessage: MutableLiveData<String?> = MutableLiveData()

     fun getLatest(accessKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val posts = withContext(Dispatchers.IO) {
                    getPostsUseCase(accessKey)
                }
                _response.value = posts
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun getConvertedValue(
        valueText: String?,
        selectedValueFrom: Double?,
        selectedValueTo: Double?
    ): String {
        val value = valueText?.toDoubleOrNull()
        return if (value != null) {
            val result = value.let { (selectedValueFrom?.let { selectedValueTo?.div(it) })?.times(it) }
            result.toString()
        } else "0"

    }
}