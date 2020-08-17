package com.emerson.bankapp.data.models.statement

import com.emerson.bankapp.data.interfaces.ResponseInterface
import com.emerson.bankapp.data.models.ErrorResponse

data class StatementListResponse(
    val statementList: List<StatementItemResponse>,
    override val error: ErrorResponse?
) : ResponseInterface