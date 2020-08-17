package com.emerson.bankapp.data.repositories

import com.emerson.bankapp.data.dataSources.statement.StatementRemoteDataSource
import com.emerson.bankapp.data.models.BaseResponse
import com.emerson.bankapp.data.models.statement.StatementListResponse

class StatementRepository(private val dataSource: StatementRemoteDataSource) {
    suspend fun getStatementList(userId: String): BaseResponse<StatementListResponse> {
        return BaseResponse.createFrom(dataSource.getStatementList(userId))
    }
}