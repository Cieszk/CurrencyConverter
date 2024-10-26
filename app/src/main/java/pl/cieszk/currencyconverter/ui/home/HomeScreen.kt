package pl.cieszk.currencyconverter.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import pl.cieszk.currencyconverter.R
import pl.cieszk.currencyconverter.viewmodel.CurrencyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: CurrencyViewModel) {
    var selectedCurrency by remember { mutableStateOf("PLN") }

    LaunchedEffect(key1 = Unit) {
        viewModel.getLatestRates(selectedCurrency)
    }

    val rates = viewModel.currencyRates.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.currency_converter),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = selectedCurrency,
            onValueChange = { selectedCurrency = it },
            label = { Text(stringResource(R.string.enter_currency_code)) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { viewModel.getLatestRates(selectedCurrency) }) {
            Text(text = stringResource(R.string.refresh_rates))
        }

        Spacer(modifier = Modifier.height(16.dp))

        rates.getOrNull()?.let { currencyRateResponse ->
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(currencyRateResponse.conversion_rates.entries.toList()) { rate ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = rate.key, fontWeight = FontWeight.Bold)
                        Text(text = rate.value.toString())
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}
