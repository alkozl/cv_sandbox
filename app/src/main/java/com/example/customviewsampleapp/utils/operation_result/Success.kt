package com.example.customviewsampleapp.utils.operation_result
data class Success<out T>(
    val data: T
) : OperationResult<T>