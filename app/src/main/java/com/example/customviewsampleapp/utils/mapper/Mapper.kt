package com.example.customviewsampleapp.utils.mapper

interface Mapper<I, O> {
    fun convert(input: I): O
}