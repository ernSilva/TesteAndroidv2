package com.emerson.bankapp.commons.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidCPF(): Boolean {
    val cpfClean = this.replace(".", "").replace("-", "")

    if (cpfClean.length != 11) return false

    try {
        val number = cpfClean.toLong()
    } catch (e: Exception) {
        return false
    }

    val dvCurrent10 = cpfClean.substring(9, 10).toInt()
    val dvCurrent11 = cpfClean.substring(10, 11).toInt()
    val cpfNineFirst = IntArray(9)
    var i = 9
    while (i > 0) {
        cpfNineFirst[i - 1] = cpfClean.substring(i - 1, i).toInt()
        i--
    }

    val sumProductNine = IntArray(9)
    var weight = 10
    var position = 0
    while (weight >= 2) {
        sumProductNine[position] = weight * cpfNineFirst[position]
        weight--
        position++
    }

    var dvForTenthDigit = sumProductNine.sum() % 11
    dvForTenthDigit = 11 - dvForTenthDigit

    if (dvForTenthDigit > 9) {
        dvForTenthDigit = 0
    }
    if (dvForTenthDigit != dvCurrent10) {
        return false
    }

    val cpfTenFirst = cpfNineFirst.copyOf(10)
    cpfTenFirst[9] = dvCurrent10

    val sumProductTen = IntArray(10)
    var w = 11
    var p = 0
    while (w >= 2) {
        sumProductTen[p] = w * cpfTenFirst[p]
        w--
        p++
    }

    var dvForEleventhDigit = sumProductTen.sum() % 11
    dvForEleventhDigit = 11 - dvForEleventhDigit
    if (dvForEleventhDigit > 9) {
        dvForEleventhDigit = 0
    }
    if (dvForEleventhDigit != dvCurrent11) {
        return false
    }

    return true
}

fun String.formatBrDate(fromPattern: String, toPattern: String): String {
    return try {
        SimpleDateFormat(fromPattern, Locale.getDefault()).parse(this)?.let {
            SimpleDateFormat(toPattern, Locale.getDefault()).format(it)
        } ?: String()
    } catch (e: java.lang.Exception) {
        String()
    }
}