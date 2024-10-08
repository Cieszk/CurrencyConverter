package pl.cieszk.currencyconverter.data.api

import pl.cieszk.currencyconverter.data.model.ConversionResponse
import pl.cieszk.currencyconverter.data.model.CurrencyRateResponse
import pl.cieszk.currencyconverter.data.model.HistoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") base: String,
        @Query("apikey") apiKey: String
    ): CurrencyRateResponse

    @GET("convert")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double,
        @Query("apikey") apiKey: String
    ): ConversionResponse

    @GET("history")
    suspend fun getHistoricalRates(
        @Query("start_at") startDate: String,
        @Query("end_at") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String,
        @Query("apikey") apiKey: String
    ): HistoryResponse
}