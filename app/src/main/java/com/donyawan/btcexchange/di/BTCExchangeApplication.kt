package com.donyawan.btcexchange.di

import android.app.Application
import com.donyawan.btcexchange.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BTCExchangeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BTCExchangeApplication)
            koin.loadModules(listOf(appModule))
            koin.createRootScope()
        }
    }
}