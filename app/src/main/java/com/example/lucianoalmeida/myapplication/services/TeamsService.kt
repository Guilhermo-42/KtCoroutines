package com.example.lucianoalmeida.myapplication.services

import android.telecom.Conference
import retrofit2.Retrofit
import retrofit2.create

class TeamsService {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://nba-teams-3306.getsandbox.com")
        .build()
    var retrofitService: TeamServiceRetrofit

    init {
        retrofitService = retrofit.create(TeamServiceRetrofit::class.java)
    }

    suspend fun teams(conference: String) = retrofitService.teams(conference)
}


