package pl.cieszk.currencyconverter.data.model

data class CurrencyRateResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
