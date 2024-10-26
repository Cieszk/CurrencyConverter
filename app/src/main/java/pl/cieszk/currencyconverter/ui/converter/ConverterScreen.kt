package pl.cieszk.currencyconverter.ui.converter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.cieszk.currencyconverter.R
import pl.cieszk.currencyconverter.viewmodel.CurrencyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(viewModel: CurrencyViewModel) {
    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("PLN") }
    var toCurrency by remember { mutableStateOf("EUR") }
    val result = viewModel.conversionResult.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.converter_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(stringResource(id = R.string.amount)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = fromCurrency,
            onValueChange = { fromCurrency = it },
            label = { Text(stringResource(id = R.string.convert_from)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = toCurrency,
            onValueChange = { toCurrency = it },
            label = { Text(stringResource(id = R.string.convert_to)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (amount.isNotEmpty()) {
                    viewModel.convertCurrency(amount.toDouble(), fromCurrency, toCurrency)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.convert_button))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Conversion Result: ${result.getOrNull()?.conversion_result} $toCurrency",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}
