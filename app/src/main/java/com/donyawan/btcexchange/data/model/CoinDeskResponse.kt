package com.donyawan.btcexchange.data.model

import com.donyawan.btcexchange.domain.coindesk.CoinDeskBody
import com.google.gson.annotations.SerializedName

data class CoinDeskResponse(
    @SerializedName("time")
    var timeUpdate: time? = null,
    @SerializedName("chartName")
    var chartName: String? = null,
    @SerializedName("bpi")
    var bpi: HashMap<String?, Coin?>? = null
)

data class time(
    @SerializedName("updated")
    var updated: String? =null,
    @SerializedName("updatedISO")
    var updatedISO: String? =null,
    @SerializedName("updateduk")
    var updatedduk: String? =null
)

fun CoinDeskResponse.mapToDomain(): CoinDeskBody =
    CoinDeskBody(
        timeUpdated = timeUpdate?.updated.orEmpty(),
        timeUpdatedISO = timeUpdate?.updatedISO.orEmpty(),
        coinList = bpi?.map { it.value?.mapToDomain() } ?: listOf()
    )