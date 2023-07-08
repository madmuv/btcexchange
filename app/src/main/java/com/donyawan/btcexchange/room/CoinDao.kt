package com.donyawan.btcexchange.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CoinDao {

    @Insert
    suspend fun insert(coin: CoinEntity)

    @Insert
    suspend fun insertAll(listCoin: List<CoinEntity>)

    @Update
    suspend fun update(coin: CoinEntity)

    @Update
    suspend fun updateList(listCoin: List<CoinEntity>)

    @Query("delete from coin_table")
    suspend fun deleteAll()

    @Query("select * from coin_table order by timeUpdated desc")
    fun getAllCoin(): LiveData<List<CoinEntity>>

    @Query("select * from coin_table where code like :searchQuery order by timeUpdated desc")
    fun searchDB(searchQuery: String): LiveData<List<CoinEntity>>

}