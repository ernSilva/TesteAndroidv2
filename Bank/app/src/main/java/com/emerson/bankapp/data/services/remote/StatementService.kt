package com.emerson.bankapp.data.services.remote

import com.emerson.bankapp.commons.application.Api
import com.emerson.bankapp.data.models.statement.StatementListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StatementService {
    @GET(Api.statements)
    suspend fun listStatements(
        @Path(Api.userIdParam) userId: String
    ): Response<StatementListResponse>
}