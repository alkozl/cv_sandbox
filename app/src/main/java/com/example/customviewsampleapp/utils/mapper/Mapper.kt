package com.example.customviewsampleapp.utils.mapper

interface Mapper<I : Any?, O : Any?> {
    suspend fun convert(input: I): O? = null
}