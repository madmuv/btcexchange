package com.donyawan.btcexchange.domain.coindesk

import android.util.Log
import androidx.lifecycle.LiveData
import com.donyawan.btcexchange.data.model.CoinDeskRepository
import com.donyawan.btcexchange.data.model.mapToDomain
import com.donyawan.btcexchange.domain.coindb.RoomRepository
import com.donyawan.btcexchange.room.CoinEntity

class GetCoinDeskUseCase(
    private val repository: CoinDeskRepository,
    private val roomRepository: RoomRepository
) {
    suspend fun execute(): CoinDeskBody {
        val data = repository.getCoinDesk().mapToDomain()
        val db: List<CoinEntity> = data.let {coin ->
            coin.coinList.map {
                CoinEntity(
                    id = 0,
                    timeUpdated = coin.timeUpdated,
                    code = it?.code.orEmpty(),
                    symbol = it?.symbol.orEmpty(),
                    rate = it?.rate.orEmpty(),
                    description = it?.description.orEmpty(),
                    rate_float = it?.rate_float?.toDouble() ?:0.0
                )
            }
        }
        db.forEach {
            roomRepository.insert(it)
        }
        return data
    }

    fun getAllCoinList(): LiveData<List<CoinEntity>> {
        return roomRepository.getCoin()
    }

    fun getCoinFilter(filter: String): LiveData<List<CoinEntity>> {
        return roomRepository.searchCoin(filter)
    }
}