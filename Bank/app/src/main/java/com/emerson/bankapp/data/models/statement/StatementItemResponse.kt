package com.emerson.bankapp.data.models.statement

data class StatementItemResponse(
    val title: String,
    val desc: String,
    val date: String,
    val value: Float
)