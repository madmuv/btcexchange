package com.donyawan.btcexchange.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "coin_table")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var timeUpdated: String,
    val code: String,
    val symbol: String,
    val rate: String,
    val description: String,
    val rate_float: Double
): Parcelable
