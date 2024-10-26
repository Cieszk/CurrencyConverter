package pl.cieszk.currencyconverter.data.api

import pl.cieszk.currencyconverter.data.model.ConversionResponse
import pl.cieszk.currencyconverter.data.model.CurrencyRateResponse
import pl.cieszk.currencyconverter.data.model.HistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest/{base}")
    suspend fun getLatestRates(
        @Path("base") base: String
    ): Response<CurrencyRateResponse>

    @GET("pair/{from}/{to}/{amount}")
    suspend fun convertCurrency(
        @Path("from") from: String,
        @Path("to") to: String,
        @Path("amount") amount: Double,
    ): Response<ConversionResponse>

    @GET("history/{base}/{year}/{month}/{day}")
    suspend fun getHistoricalRates(
        @Path("base") base: String,
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day: String
    ): Response<HistoryResponse>
}