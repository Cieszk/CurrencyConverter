package pl.cieszk.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.cieszk.currencyconverter.BuildConfig
import pl.cieszk.currencyconverter.data.api.RetrofitInstance
import pl.cieszk.currencyconverter.data.model.ConversionResponse
import pl.cieszk.currencyconverter.data.model.CurrencyRateResponse
import pl.cieszk.currencyconverter.data.model.HistoryResponse

class CurrencyViewModel: ViewModel() {
    private val _currencyRate = MutableStateFlow<CurrencyRateResponse?>(null)
    val currencyRates: StateFlow<CurrencyRateResponse?> = _currencyRate

    private val _conversionResult = MutableStateFlow<ConversionResponse?>(null)
    val conversionResult: StateFlow<ConversionResponse?> = _conversionResult

    private val _historyRates = MutableStateFlow<HistoryResponse?>(null)
    val historRates: StateFlow<HistoryResponse?> = _historyRates

    private val apiKey = BuildConfig.API_KEY


    fun getLatestRates(base: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getLatestRates(base, apiKey)
            _currencyRate.value = response
        }
    }

    fun convertCurrency(amount: Double, from: String, to: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.convertCurrency(from, to , amount, apiKey)
            _conversionResult.value = response
        }
    }

    fun getHistoricalRates(startDate: String, endDate: String, base: String, currency: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getHistoricalRates(startDate, endDate, base, currency, apiKey)
            _historyRates.value = response
        }
    }
}