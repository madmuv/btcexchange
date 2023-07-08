package com.donyawan.btcexchange.data.model

import com.donyawan.btcexchange.domain.coindesk.CoinBody
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Coin(
    @SerializedName("code")
    var code: String? = null,
    @SerializedName("symbol")
    var symbol: String? = null,
    @SerializedName("rate")
    var rate: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("rate_float")
    var rate_float: BigDecimal? = null
)

fun Coin.mapToDomain() : CoinBody =
    CoinBody(
        code = code ?:"",
        symbol = symbol ?:"",
        rate = rate ?:"",
        description =  description ?:"",
        rate_float = rate_float ?: BigDecimal.ZERO
    )