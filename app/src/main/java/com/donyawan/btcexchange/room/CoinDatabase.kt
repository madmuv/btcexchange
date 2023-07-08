package com.donyawan.btcexchange.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoinEntity::class], version = 1, exportSchema = false)
abstract class CoinDatabase(): RoomDatabase() {
    abstract fun  coinDao(): CoinDao

    companion object {
        @Volatile
        private var INSTANCE: CoinDatabase? = null

        fun getDatabase(context: Context): CoinDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CoinDatabase::class.java,
                        "coin_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}