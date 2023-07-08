package com.donyawan.btcexchange.di

import androidx.room.Room
import com.donyawan.btcexchange.domain.coindesk.CoinDeskRepositoryImpl
import com.donyawan.btcexchange.data.CoinDeskService
import com.donyawan.btcexchange.domain.coindb.RoomRepository
import com.donyawan.btcexchange.data.model.CoinDeskRepository
import com.donyawan.btcexchange.domain.coindesk.GetCoinDeskUseCase
import com.donyawan.btcexchange.network.RetrofitHelper
import com.donyawan.btcexchange.room.CoinDatabase
import com.donyawan.btcexchange.ui.MainActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

    single {
        RetrofitHelper
    }

    single<CoinDeskService> {
        RetrofitHelper.getInstance().create(CoinDeskService::class.java)
    }

    single<CoinDeskRepository> {
        CoinDeskRepositoryImpl(
            get()
        )
    }

    single{
        RoomRepository(get())
    }

    single {
        Room.databaseBuilder((androidApplication()), CoinDatabase::class.java, "coin_table").build()
    }

    single { get<CoinDatabase>().coinDao() }

    factory { GetCoinDeskUseCase(get(), get()) }

    viewModel {
        MainActivityViewModel(
            get()
        )
    }
}