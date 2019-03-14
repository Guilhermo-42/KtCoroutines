package com.example.lucianoalmeida.myapplication.services

import com.example.lucianoalmeida.myapplication.extensions.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamsService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://nba-retrieveTeams-3306.getsandbox.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var retrofitService: TeamsServiceRetrofit

    init {
        retrofitService = retrofit.create(TeamsServiceRetrofit::class.java)
    }

    suspend fun retrieveTeams(conference: String) = retrofitService.teams(conference).await()

}