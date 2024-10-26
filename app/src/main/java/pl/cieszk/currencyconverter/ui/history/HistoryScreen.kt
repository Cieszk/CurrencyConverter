package pl.cieszk.currencyconverter.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.cieszk.currencyconverter.R
import pl.cieszk.currencyconverter.viewmodel.CurrencyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(viewModel: CurrencyViewModel) {
    var baseCurrency by remember { mutableStateOf("USD") }
    var year by remember { mutableStateOf("2024") }
    var month by remember { mutableStateOf("10") }
    var day by remember { mutableStateOf("01") }

    val historyState = viewModel.historyRates.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.historical_rates),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = baseCurrency,
            onValueChange = { baseCurrency = it },
            label = { Text(stringResource(R.string.base_currency)) },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        TextField(
            value = year,
            onValueChange = { year = it },
            label = { Text(stringResource(R.string.year)) },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        TextField(
            value = month,
            onValueChange = { month = it },
            label = { Text(stringResource(R.string.month)) },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        TextField(
            value = day,
            onValueChange = { day = it },
            label = { Text(stringResource(R.string.day)) },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        historyState.getOrNull()?.let { historyResponse ->
            Text(
                text = "Base: ${historyResponse.base_code}, Date: ${historyResponse.year}-${historyResponse.month}-${historyResponse.day}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(historyResponse.conversion_rates.entries.toList()) { rate ->
                    Text(text = "${rate.key}: ${rate.value}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Button(
            onClick = {
                viewModel.getHistoricalRates(baseCurrency, year, month, day)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.fetch_historical_data))
        }
    }
}
