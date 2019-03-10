package com.example.lucianoalmeida.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.*

class MainActivity: AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext get() =  Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}

