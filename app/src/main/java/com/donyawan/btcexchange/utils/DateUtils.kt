package com.donyawan.btcexchange.utils

import android.util.Log
import org.joda.time.chrono.BuddhistChronology
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(
    pattern: String
): Date? =
    try {
        SimpleDateFormat(pattern, Locale.US).parse(this)
    } catch (e: Exception) {
        null
    }

fun String.toDateFormat(
    lang: String,
    serverPattern: String = SERVER_TIME_FORMAT,
    displayPattern: String = dd_MMM_HHmmss
): String =
    try {
        DateTimeFormat
            .forPattern(serverPattern)
            .parseDateTime(this)
            .let {
                when (lang.lowercase(Locale.US)) {
                    LANG_TH -> it
                        .withChronology(BuddhistChronology.getInstance())
                        .toString(displayPattern, thLocale)
                    else -> it.toString(displayPattern, Locale.US)
                }
            }
    } catch (e: IllegalArgumentException) {
        Log.d("date",e.toString())
        ""
    }

const val SERVER_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
const val dd_MMM_HHmmss = "dd MMM HH:mm:ss"
const val LANG_TH = "th"
private val thLocale = Locale("th", "TH")