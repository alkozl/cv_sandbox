package com.example.customviewsampleapp.utils.operation_result

fun <T> T.asSuccessResult(): Success<T> {
    return Success(this)
}