package com.example.lucianoalmeida.myapplication.services

import com.example.lucianoalmeida.myapplication.extensions.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamsService {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://nba-teams-3306.getsandbox.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var retrofitService: TeamsServiceRetrofit

    init {
        retrofitService = retrofit.create(TeamsServiceRetrofit::class.java)
    }

    suspend fun teams(conference: String) = retrofitService.teams(conference).await()
}


