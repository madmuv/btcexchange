package com.donyawan.btcexchange.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.donyawan.btcexchange.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConverterActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConverterLayout(viewModel)
        }
        viewModel.getAllCoin().observe(this@ConverterActivity) {
            viewModel.setCoinList(it)
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConverterLayout(viewModel: MainActivityViewModel) {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }
    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val currencyList = listOf(
            stringResource(id = R.string.converter_eur_label),
            stringResource(id = R.string.converter_usd_label),
            stringResource(id = R.string.converter_gbp_label),
        )

        var selectedItemIndex by remember { mutableStateOf(0) }

        TextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            placeholder = { Text(text = stringResource(R.string.converter_hint)) },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Decimal,
                autoCorrect = true
            ),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif
            ),
            singleLine = true,
            keyboardActions = KeyboardActions {
                viewModel.convertToBTC(currencyList[selectedItemIndex], inputValue.value.text)
            }
        )

        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = { expanded.value = !expanded.value },
            modifier = Modifier.fillMaxWidth()
        ) {

            TextField(
                value = currencyList[selectedItemIndex],
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) }
            )

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {
                currencyList.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedItemIndex = index
                            expanded.value = false
                            viewModel.convertToBTC(currencyList[selectedItemIndex], inputValue.value.text)
                        }) {
                        Text(
                            text = item,
                            fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                        )
                    }
                }
            }
        }

        Text(text = stringResource(id = R.string.converter_to_btc, viewModel.btcValue.observeAsState(
            initial = "0.0"
        ).value))
    }
}