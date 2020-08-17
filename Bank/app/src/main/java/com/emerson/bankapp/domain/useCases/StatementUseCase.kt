package com.emerson.bankapp.domain.useCases

import com.emerson.bankapp.data.models.BaseResponse
import com.emerson.bankapp.data.repositories.StatementRepository
import com.emerson.bankapp.domain.models.Statement

class StatementUseCase(private val repository: StatementRepository) {
    suspend fun getStatementList(userId: String): BaseResponse<List<Statement>> {
        return when (val response = repository.getStatementList(userId)) {
            is BaseResponse.Success -> {
                BaseResponse.Success(response.data.statementList.map { Statement.createFrom(it) })
            }
            is BaseResponse.Error -> response
        }
    }
}