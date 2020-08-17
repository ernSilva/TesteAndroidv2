package com.emerson.bankapp.commons.extensions

import android.os.Build
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Float.formatBrCurrency(): String {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
        this.formatBrCurrencyNewVersions()
    } else {
        this.formatBrCurrencyOldVersions()
    }
}

fun Float.formatBrCurrencyNewVersions(): String {
    return NumberFormat
        .getCurrencyInstance(Locale("pt", "BR"))
        .format(this)
}

fun Float.formatBrCurrencyOldVersions(): String {
    val brFormat = NumberFormat
        .getCurrencyInstance()
            as DecimalFormat
    val currency = "R$"
    brFormat.isGroupingUsed = true
    brFormat.positivePrefix = currency
    brFormat.negativePrefix = "-$currency"
    brFormat.minimumFractionDigits = 2
    brFormat.maximumFractionDigits = 2

    return brFormat.format(this)
}