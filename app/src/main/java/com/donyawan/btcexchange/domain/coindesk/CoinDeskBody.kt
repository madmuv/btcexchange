package com.donyawan.btcexchange.domain.coindesk

data class CoinDeskBody(
    var timeUpdated: String,
    var timeUpdatedISO: String,
    var coinList: List<CoinBody?>
)
