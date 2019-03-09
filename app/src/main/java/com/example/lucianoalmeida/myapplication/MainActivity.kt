package com.example.lucianoalmeida.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.*

class MainActivity(override val coroutineContext: CoroutineContext) : AppCompatActivity(), CoroutineScope {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}

