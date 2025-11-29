package com.merteroglu286.domain.result

import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

suspend fun <T> Result<T>.doOnSuccess(onSuccess: suspend (T) -> Unit): Result<T> {
    if (this is Result.Success<T>) {
        if (coroutineContext.isActive) {
            onSuccess(this.data)
        }
    }
    return this
}

suspend fun <T> Result<T>.doOnError(onError: () -> Unit): Result<T> {
    if (!this.isSuccess() && coroutineContext.isActive) {
        onError()
    }
    return this
}

suspend fun <T> Result<T>.doOnEmpty(onEmpty: suspend () -> Unit): Result<T> {
    if (this is Result.Empty) {
        if (coroutineContext.isActive) {
            onEmpty()
        }
    }
    return this
}

suspend fun <T, R> Result<T>.map(mapper: suspend (T) -> R): Result<R> {
    return when (this) {
        is Result.Success<T> -> Result.success(mapper(this.data))
        is Result.Error<T> -> Result.error(this.errorMessage())
        is Result.Empty<T> -> Result.empty()
    }
}

suspend fun <F, S, R> Result<F>.merge(
    lazy: suspend () -> Result<S>,
    merger: (F?, S?) -> R
): Result<R> {
    return when (this) {
        is Result.Success<F> -> {
            when (val second = lazy()) {
                is Result.Success<S> -> {
                    Result.success(merger(this.data, second.data))
                }

                is Result.Error<S> -> {
                    Result.error(second.errorMessage())
                }

                is Result.Empty<S> -> {
                    Result.success(merger(this.data, null))
                }
            }
        }

        is Result.Error<F> -> {
            Result.error(this.errorMessage())
        }

        is Result.Empty<F> -> {
            when (val second = lazy()) {
                is Result.Success<S> -> {
                    Result.success(merger(null, second.data))
                }

                is Result.Error<S> -> {
                    Result.error(second.errorMessage())
                }

                is Result.Empty<S> -> {
                    Result.empty()
                }
            }
        }
    }
}