package com.eziosoft.parisinnumbers.data

import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T?> {
    return if (isSuccessful) {
        Result.success(body())
    } else {
        Result.failure(Exception(message()))
    }
}
