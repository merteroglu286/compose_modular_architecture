package com.merteroglu286.data.mapper

import com.merteroglu286.data.response.ErrorResponse
import com.merteroglu286.domain.model.Error

// mapping errorResponse to Error model
fun ErrorResponse.toDomain(code: Int): Error {
    return Error(
        errorCode = code,
        errorMessage = errorMessage.orEmpty(),
        errorFieldList = errorFieldList ?: emptyList()
    )
}