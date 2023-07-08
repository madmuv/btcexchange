package com.donyawan.btcexchange.domain.coindesk

import java.math.BigDecimal

data class CoinBody(
    var code: String,
    var symbol: String,
    var rate: String,
    var description: String,
    var rate_float: BigDecimal
)
