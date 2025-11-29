package com.merteroglu286.domain.usecase

import com.merteroglu286.domain.model.ErrorMessage
import com.merteroglu286.domain.result.Result

interface UseCase<R> {

    suspend fun onSuccess(success: Result.Success<R>)

    suspend fun onEmpty()

    suspend fun onError(errorMessage: ErrorMessage)
}