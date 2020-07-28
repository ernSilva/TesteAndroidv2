package com.emerson.bankapp.utils.extensions

import android.util.Log
import com.squareup.moshi.Moshi
import java.text.SimpleDateFormat
import java.util.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

fun String.Companion.empty() = ""

fun localeBrazil() = Locale("pt", "BR")

fun String.dateToFormatBr(): String {
    val date = SimpleDateFormat("yyyy-MM-dd", localeBrazil()).parse(this)
    return SimpleDateFormat("dd/MM/yyyy", localeBrazil()).format(date)
}

fun String.accountFormatBr(): String {
    Log.d("accountFormatBr","${this} ${this.substring(0, 2)}")
    Log.d("accountFormatBr","${this} ${this.substring(2, 6)}")
    Log.d("accountFormatBr","${this} ${this.substring(7, 9)}")
    return "${this.substring(0, 2)}.${this.substring(2, 7)}-${this.substring(7, 9)}"

}

inline fun <reified T> Moshi.fromJson(json: String): T? =
    if (json.isNotBlank()) this.adapter(T::class.java).fromJson(json) else null

inline fun <reified T> Moshi.toJson(t: T): String = this.adapter(T::class.java).toJson(t) ?: ""



@JsonClass(generateAdapter = true)
data class ResponseError(
    @Json(name = "code") val code: Int?,
    @Json(name = "message") val message: String?
)