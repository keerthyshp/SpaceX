package com.keerthy.spacex.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatDate(strDate: String): String? {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
        val dfNew = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(strDate)
        df.timeZone = TimeZone.getDefault()
        return date?.let { dfNew.format(it) }
    }

}