package com.merteroglu286.domain.result

import com.merteroglu286.domain.model.ErrorMessage
import com.merteroglu286.domain.usecase.UseCase

sealed class Result<T> {
    abstract fun isSuccess(): Boolean

    open fun errorMessage(): ErrorMessage? = null

    abstract suspend fun accept(useCase: UseCase<T>)

    class Success<T>(val data: T) : Result<T>() {
        override fun isSuccess(): Boolean = true

        override suspend fun accept(useCase: UseCase<T>) {
            useCase.onSuccess(this)
        }
    }

    class Error<T>(val errorMessage: ErrorMessage) : Result<T>() {
        override fun isSuccess(): Boolean = false

        override fun errorMessage(): ErrorMessage = errorMessage

        override suspend fun accept(useCase: UseCase<T>) {
            useCase.onError(errorMessage)
        }
    }

    class Empty<T>() : Result<T>() {
        override fun isSuccess(): Boolean = true

        override suspend fun accept(useCase: UseCase<T>) {
            useCase.onEmpty()
        }
    }

    companion object{
        fun <T> success(data: T) = Success(data)
        fun <T> error(errorMessage: ErrorMessage) = Error<T>(errorMessage)
        fun <T> empty() = Empty<T>()

    }
}