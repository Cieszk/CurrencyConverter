package pl.cieszk.currencyconverter.viewmodel

import android.util.Log
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
import retrofit2.HttpException
import java.io.IOException

class CurrencyViewModel : ViewModel() {

    private val _currencyRate = MutableStateFlow<Result<CurrencyRateResponse?>>(Result.success(null))
    val currencyRates: StateFlow<Result<CurrencyRateResponse?>> = _currencyRate

    private val _conversionResult = MutableStateFlow<Result<ConversionResponse?>>(Result.success(null))
    val conversionResult: StateFlow<Result<ConversionResponse?>> = _conversionResult

    private val _historyRates = MutableStateFlow<Result<HistoryResponse?>>(Result.success(null))
    val historyRates: StateFlow<Result<HistoryResponse?>> = _historyRates

    private val apiKey = BuildConfig.API_KEY

    private fun logError(message: String, throwable: Throwable? = null) {
        Log.e("CurrencyViewModel", message, throwable)
    }

    fun getLatestRates(selectedCurrency: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getLatestRates(selectedCurrency)
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("API Response", body.toString())
                    _currencyRate.value = Result.success(body)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Unexpected error when fetching latest rates", e)
            }
        }
    }

    fun convertCurrency(amount: Double, from: String, to: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.convertCurrency(from, to, amount)
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("API Response", body.toString())
                    _conversionResult.value = Result.success(body)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Unexpected error while converting currencies", e)
            }
        }
    }

    fun getHistoricalRates(base: String, year: String, month: String, day: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getHistoricalRates(base, year, month, day)
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("API Response", body.toString())
                    _historyRates.value = Result.success(body)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Unexpected error when fetching historical rates", e)
            }
        }
    }
}
