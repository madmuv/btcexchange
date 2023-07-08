package com.donyawan.btcexchange.data

import com.donyawan.btcexchange.data.model.CoinDeskResponse
import retrofit2.http.GET

interface CoinDeskService {

    @GET("v1/bpi/currentprice.json")
    suspend fun getCoinInfo(): CoinDeskResponse
}