package pl.cieszk.currencyconverter.data.model

data class HistoryResponse(
    val base: String,
    val start_at: String,
    val end_at: String,
    val rates: Map<String, Map<String, Double>>
)
