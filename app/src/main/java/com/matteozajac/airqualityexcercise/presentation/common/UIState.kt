package com.matteozajac.airqualityexcercise.presentation.common

sealed class UIState<out T : Any> {
    object Initial : UIState<Nothing>()
    object Loading : UIState<Nothing>()

    data class Success<out T : Any>(val value: T) : UIState<T>()
    data class Failure(val message: String) : UIState<Nothing>()
}