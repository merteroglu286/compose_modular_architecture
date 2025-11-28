package com.merteroglu286.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.merteroglu286.domain.model.ErrorMessage
import com.merteroglu286.presentation.views.EmptyScreenView
import com.merteroglu286.presentation.views.ErrorFullScreenView
import com.merteroglu286.presentation.views.ErrorPopupView
import com.merteroglu286.presentation.views.LoadingFullScreenView
import com.merteroglu286.presentation.views.LoadingPopupView

sealed class ScreenState<out S, O> {
    // S for ViewState and O for Output

    // Content State
    class ScreenContent<S, O>(val viewState: S) : ScreenState<S, O>()

    // Loading States
    // 1- Popup Loading State
    data class LoadingPopup<S, O>(
        val viewState: S,
        @StringRes val loadingMessage: Int = R.string.loading,
    ) : ScreenState<S, O>()

    // 2- Full Screen Loading State
    data class LoadingFullScreen<S, O>(
        val viewState: S,
        @StringRes val loadingMessage: Int = R.string.loading,
    ) : ScreenState<S, O>()

    // Error States
    // 1- Popup Error State
    data class ErrorPopup<S, O>(
        val viewState: S,
        val errorMessage: ErrorMessage,
    ) : ScreenState<S, O>()

    // 2- Full Screen Error State
    data class ErrorFullScreen<S, O>(
        val viewState: S,
        val errorMessage: ErrorMessage,
    ) : ScreenState<S, O>()

    // Empty State
    data class Empty<S, O>(
        val viewState: S,
        @StringRes val emptyMessage: Int = R.string.no_data,
    ) : ScreenState<S, O>()

    // Success State
    data class Success<S, O>(val output: O) : ScreenState<S, O>()

    // ScreenContent

    @Composable
    fun onUiState(action: @Composable (S) -> Unit): ScreenState<S, O> {
        if (this is ScreenContent) {
            action(viewState)
        }
        return this
    }

    @Composable
    fun onLoadingState(action: @Composable (S) -> Unit): ScreenState<S, O> {
        if (this is LoadingPopup) {
            action(viewState)
        } else if (this is LoadingFullScreen) {
            action(viewState)
        }
        return this
    }

    fun onSuccessState(action: (O) -> Unit): ScreenState<S, O> {
        if (this is Success) {
            action(output)
        }
        return this
    }

    @Composable
    fun onErrorState(action: @Composable (S) -> Unit): ScreenState<S, O> {
        if (this is ErrorPopup) {
            action(viewState)
        } else if (this is ErrorFullScreen) {
            action(viewState)
        }
        return this
    }

    fun onEmptyState(action: () -> Unit): ScreenState<S, O> {
        if (this is Empty) {
            action()
        }
        return this
    }

    companion object {
        @Composable
        fun <S, O> of(
            retryAction: () -> Unit = {},
            screenState: ScreenState<S, O>,
            blocK: @Composable ScreenState<S, O>.() -> Unit,
        ): ScreenState<S, O> {
            screenState.blocK() // show this first before doing any thing

            when (screenState) {
                is Empty -> EmptyScreenView(screenState.emptyMessage)
                is ErrorFullScreen -> ErrorFullScreenView(screenState.errorMessage, retryAction)
                is ErrorPopup -> ErrorPopupView(screenState.errorMessage, retryAction)
                is LoadingFullScreen -> LoadingFullScreenView(screenState.loadingMessage)
                is LoadingPopup -> LoadingPopupView(screenState.loadingMessage)
                else -> {}
            }
            return screenState
        }
    }
}

/*
package com.merteroglu286.presentation

import androidx.annotation.StringRes
import com.merteroglu286.domain.model.ErrorMessage

sealed class ScreenState<out S, O> {
    data class Content<S, O>(val uiState: S) : ScreenState<S, O>()
    data class Loading<S, O>(
        val uiState: S,
        @StringRes val loadingMessage: Int
    ) : ScreenState<S, O>()
    data class Error<S, O>(
        val uiState: S,
        val errorMessage: ErrorMessage
    ) : ScreenState<S, O>()
    data class Empty<S, O>(
        val uiState: S,
        @StringRes val emptyMessage: Int
    ) : ScreenState<S, O>()
    data class Success<S, O>(val output: O) : ScreenState<S, O>()
}*/
