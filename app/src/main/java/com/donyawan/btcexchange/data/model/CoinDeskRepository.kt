package com.donyawan.btcexchange.data.model

import com.donyawan.btcexchange.data.model.CoinDeskResponse

interface CoinDeskRepository {
    suspend fun getCoinDesk(): CoinDeskResponse
}