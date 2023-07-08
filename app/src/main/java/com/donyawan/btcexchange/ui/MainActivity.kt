package com.donyawan.btcexchange.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.widget.doOnTextChanged
import androidx.room.util.query
import com.donyawan.btcexchange.R
import com.donyawan.btcexchange.databinding.ActivityMainBinding
import com.donyawan.btcexchange.room.CoinEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setupView()
        viewModelObserve()
        getCoinInfo()
    }

    private fun setupView() {
        binding.autoComplete.apply {

            doOnTextChanged { text, start, before, count ->
                viewModel.getCoinList(text.toString())
            }

            setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    viewModel.getCoinList(textView.text.toString())
                }
                true
            }
        }

        binding.btnExchange.setOnClickListener {
            this.startActivity(
                Intent(this, ConverterActivity::class.java)
            )
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getCoinList()
            binding.autoComplete.setText(getString(R.string.converter_all_label))
        }

        binding.composeView.setContent {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.main_exchange_label),
                    color = colorResource(id = R.color.teal_200),
                )

                CoinList(coinList = viewModel.coinList.observeAsState())
            }
        }

        viewModel.getCoinList()
    }

    private fun getCoinInfo() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.fetchData()
        }, 1000)
    }


    private fun viewModelObserve() {
        with(viewModel) {
            coinList.observe(this@MainActivity) {
                binding.refreshLayout.isRefreshing = false
            }

            getCoinEvent.observe(this@MainActivity) {
                viewModel.getAllCoin().observe(this@MainActivity) {
                    viewModel.setCoinList(it)
                }
            }

            searchCoinEvent.observe(this@MainActivity) { query ->
                viewModel.searchCoin("%$query%").observe(this@MainActivity) {
                    viewModel.setCoinList(it)
                }
            }
        }
    }
}

@Composable
fun CoinList(
    coinList: State<List<CoinEntity>?>
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
       items(items = coinList.value ?: listOf()) { item ->
           CoinItem(item = item)
       }
    }
}

@Composable
fun CoinItem(item: CoinEntity) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text =
            when (item.code) {
                "GBP" -> "🇬🇧"
                "EUR" -> "\uD83C\uDDEA\uD83C\uDDFA"
                else -> "\uD83C\uDDFA\uD83C\uDDF8"
            }
        )
        Text(text = item.code)
        Text(text = item.rate)
    }
}

@Preview
@Composable
fun Preview_Coin_item() {
    val item = CoinEntity(
        id = 0L,
        timeUpdated = "Jul 6, 2023 17:09:00 UTC",
        code = "GBP",
        symbol = "&euro;",
        rate = "29,548.8620",
        description = "",
        rate_float = 29548.862
    )
    CoinItem(item = item)
}