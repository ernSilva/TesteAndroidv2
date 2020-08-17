package com.emerson.bankapp.domain.models

import com.emerson.bankapp.commons.extensions.formatBrCurrency
import com.emerson.bankapp.commons.extensions.formatBrDate
import com.emerson.bankapp.data.models.statement.StatementItemResponse

data class Statement(
    val title: String,
    val description: String,
    val date: String,
    val value: Float
) {
    fun getDateFormatted(): String {
        return date.formatBrDate("yyyy-MM-dd", "dd/MM/yyyy")
    }

    fun getValueFormatted(): String {
        return value.formatBrCurrency()
    }

    companion object {
        fun createFrom(item: StatementItemResponse): Statement {
            return Statement(
                item.title,
                item.desc,
                item.date,
                item.value
            )
        }
    }
}