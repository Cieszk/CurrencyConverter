package pl.cieszk.currencyconverter.data.model

data class HistoryResponse(
    val base_code: String,
    val year: String,
    val month: String,
    val day: String,
    val conversion_rates: Map<String, Double>
)
