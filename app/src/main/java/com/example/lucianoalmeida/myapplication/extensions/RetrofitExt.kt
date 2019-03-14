package com.example.lucianoalmeida.myapplication.extensions

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { continuation ->
    enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    continuation.resume(it)
                }
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWith(Result.failure(t))
        }

    })

    continuation.invokeOnCancellation {
        this@await.cancel()
    }
}