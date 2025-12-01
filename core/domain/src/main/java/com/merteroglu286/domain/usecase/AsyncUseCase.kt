package com.merteroglu286.domain.usecase

import com.merteroglu286.domain.model.Error
import com.merteroglu286.domain.result.Result

abstract class AsyncUseCase<I, R> : UseCase<R> {
    private lateinit var success: suspend (R) -> Unit
    private lateinit var empty: suspend () -> Unit
    private lateinit var error: suspend (Error) -> Unit

    suspend fun execute(
        input: I,
        success: suspend (R) -> Unit = {},
        empty: suspend () -> Unit = {},
        error: suspend (Error) -> Unit = {}
    ) {
        this.success = success
        this.empty = empty
        this.error = error

        run(input).accept(this)
    }

    abstract suspend fun run(input: I): Result<R>

    override suspend fun onSuccess(success: Result.Success<R>) {
        success(success.data)
    }

    override suspend fun onEmpty() {
        empty()
    }

    override suspend fun onError(error: Error) {
        error(error)
    }

}