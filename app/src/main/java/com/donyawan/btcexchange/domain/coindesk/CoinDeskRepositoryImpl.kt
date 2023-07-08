package com.donyawan.btcexchange.domain.coindesk

import com.donyawan.btcexchange.data.CoinDeskService
import com.donyawan.btcexchange.data.model.CoinDeskResponse
import com.donyawan.btcexchange.data.model.CoinDeskRepository

class CoinDeskRepositoryImpl(
    private val service: CoinDeskService
): CoinDeskRepository {
    override suspend fun getCoinDesk(): CoinDeskResponse {
        return service.getCoinInfo()
    }
}