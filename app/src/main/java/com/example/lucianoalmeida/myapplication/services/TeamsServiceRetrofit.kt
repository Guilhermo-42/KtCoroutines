package com.example.lucianoalmeida.myapplication.services

import com.example.lucianoalmeida.myapplication.model.Team
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamsServiceRetrofit {

    @GET("/")
    fun teams(@Query("conference") conference: String): Call<List<Team>>

}