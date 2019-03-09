package com.example.lucianoalmeida.myapplication.extensions

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                it.resume(response.body()!!)
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            it.resumeWith(Result.failure(t))
        }
    })
    it.invokeOnCancellation {
        this@await.cancel()
    }
}