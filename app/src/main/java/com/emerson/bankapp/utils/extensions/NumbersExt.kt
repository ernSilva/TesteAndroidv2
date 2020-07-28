package com.emerson.bankapp.utils.extensions

import java.math.BigDecimal
import java.text.NumberFormat

fun BigDecimal.formatCurrency() = NumberFormat.getCurrencyInstance(localeBrazil()).format(this)!!

fun Double.formatCurrency() = NumberFormat.getCurrencyInstance(localeBrazil()).format(this)!!