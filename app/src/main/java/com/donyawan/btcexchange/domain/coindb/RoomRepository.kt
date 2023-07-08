package com.donyawan.btcexchange.domain.coindb

import androidx.lifecycle.LiveData
import com.donyawan.btcexchange.room.CoinDao
import com.donyawan.btcexchange.room.CoinEntity

class RoomRepository(val coinDao: CoinDao) {

    suspend fun insert(coinEntity: CoinEntity) = coinDao.insert(coinEntity)

    suspend fun insertAll(coinList: List<CoinEntity>) = coinDao.insertAll(coinList)

    suspend fun update(coinEntity: CoinEntity) = coinDao.update(coinEntity)

    suspend fun updateAll(coinList: List<CoinEntity>) = coinDao.updateList(coinList)

    suspend fun deleteAll() = coinDao.deleteAll()

    fun getCoin(): LiveData<List<CoinEntity>> = coinDao.getAllCoin()

    fun searchCoin(searchQuery: String) :LiveData<List<CoinEntity>> {
        return coinDao.searchDB(searchQuery)
    }
}