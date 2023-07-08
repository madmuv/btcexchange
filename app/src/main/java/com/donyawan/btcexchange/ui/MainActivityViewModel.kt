package com.donyawan.btcexchange.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donyawan.btcexchange.data.model.Coin
import com.donyawan.btcexchange.domain.coindesk.CoinBody
import com.donyawan.btcexchange.domain.coindesk.GetCoinDeskUseCase
import com.donyawan.btcexchange.room.CoinEntity
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getCoinDeskUseCase: GetCoinDeskUseCase
): ViewModel() {

    val _coinList = MutableLiveData<List<CoinEntity>>()
    val searchCoinEvent = MutableLiveData<String>()
    val getCoinEvent = MutableLiveData<Unit>()
    val btcValue = MutableLiveData<String>()
    var coinList : LiveData<List<CoinEntity>>
        get() = _coinList

    init {
        coinList = getCoinDeskUseCase.getAllCoinList()
    }

    fun fetchData() {
        viewModelScope.launch {
            getCoinDeskUseCase.execute()
        }
    }

    fun getCoinList(filter: String? = null) {
        viewModelScope.launch {
            if (filter == "ALL" || filter == null) {
                getCoinEvent.value = Unit
            } else {
                searchCoinEvent.value = filter
            }
        }
    }

    fun getAllCoin(): LiveData<List<CoinEntity>> {
        return getCoinDeskUseCase.getAllCoinList()
    }

    fun searchCoin(filter:String? = null): LiveData<List<CoinEntity>> {
        return getCoinDeskUseCase.getCoinFilter(filter ?:"")
    }

    fun setCoinList(coin: List<CoinEntity>) {
        _coinList.value = coin
    }

    fun convertToBTC(currency: String, number: String) {
        val rated = coinList.value?.firstOrNull { it.code == currency }?.rate_float ?:0.0
        btcValue.value = ((1/rated) * number.toDouble()).toString()
    }
}