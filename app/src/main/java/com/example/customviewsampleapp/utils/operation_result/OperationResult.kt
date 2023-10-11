package com.example.customviewsampleapp.utils.operation_result

sealed interface OperationResult<out T> {
    fun <O> flatMapIfSuccess(block: (T) -> OperationResult<O>): OperationResult<O> {
        return if (this is Success) {
            block(this.data)
        } else {
            NothingResult
        }
    }

    fun onSuccess(block: (T) -> Unit)  {
        if (this is Success) {
            block(this.data)
        } else {
            NothingResult
        }
    }
}
