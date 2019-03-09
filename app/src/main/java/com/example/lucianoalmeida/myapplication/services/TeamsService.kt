package com.example.lucianoalmeida.myapplication.services

import retrofit2.Retrofit

class TeamsService {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://nba-teams-3306.getsandbox.com")
        .build()
    var retrofitService: TeamsServiceRetrofit

    init {
        retrofitService = retrofit.create(TeamsServiceRetrofit::class.java)
    }

    suspend fun teams(conference: String) = retrofitService.teams(conference)
}


