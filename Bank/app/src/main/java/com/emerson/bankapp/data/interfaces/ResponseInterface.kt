package com.emerson.bankapp.data.interfaces

import com.emerson.bankapp.data.models.ErrorResponse

interface ResponseInterface {
    val error: ErrorResponse?
}