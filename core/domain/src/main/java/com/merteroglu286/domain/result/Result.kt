package com.merteroglu286.domain.result

import com.merteroglu286.domain.usecase.UseCase

sealed class Result<T> {
    abstract fun isSuccess(): Boolean

    open fun errorMessage(): com.merteroglu286.domain.model.Error? = null

    abstract suspend fun accept(useCase: UseCase<T>)

    class Success<T>(val data: T) : Result<T>() {
        override fun isSuccess(): Boolean = true

        override suspend fun accept(useCase: UseCase<T>) {
            useCase.onSuccess(this)
        }
    }

    class Error<T>(val error: com.merteroglu286.domain.model.Error) : Result<T>() {
        override fun isSuccess(): Boolean = false

        override fun errorMessage(): com.merteroglu286.domain.model.Error = error

        override suspend fun accept(useCase: UseCase<T>) {
            useCase.onError(error)
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
        fun <T> error(error: com.merteroglu286.domain.model.Error) = Error<T>(error)
        fun <T> empty() = Empty<T>()

    }
}