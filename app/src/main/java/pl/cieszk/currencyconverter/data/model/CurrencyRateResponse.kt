package pl.cieszk.currencyconverter.data.model

data class CurrencyRateResponse(
    val base_code: String,
    val conversion_rates: Map<String, Double>
)