package com.example.customviewsampleapp.utils.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

inline fun CoroutineScope.doOnMain(crossinline block: suspend () -> Unit) {
    launch(Dispatchers.Main) {
        block()
    }
}

inline fun ViewModel.doOnIO(crossinline block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}