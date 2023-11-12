package com.example.customviewsampleapp.utils.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

inline fun ViewModel.launchOnMain(crossinline block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.Main) {
        block()
    }
}

suspend fun <T> doOnIO(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> T
): T {
    return withContext(dispatcher) {
        block()
    }
}

suspend fun <T> doOnDefault(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    block: suspend CoroutineScope.() -> T
): T {
    return withContext(dispatcher) {
        block()
    }
}