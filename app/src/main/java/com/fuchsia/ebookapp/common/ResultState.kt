package com.fuchsia.ebookapp.common

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val exception: String) : ResultState<Nothing>()


}