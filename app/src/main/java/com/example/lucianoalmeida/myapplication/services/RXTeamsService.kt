package com.example.lucianoalmeida.myapplication.services

import com.example.lucianoalmeida.myapplication.model.Team
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RXTeamsService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://nba-retrieveTeams-3306.getsandbox.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var retrofitService: TeamsServiceRetrofit

    val teamsObservable: BehaviorSubject<List<Team>> = BehaviorSubject.create()

    val errorObservable: PublishSubject<String> = PublishSubject.create()

    init {
        retrofitService = retrofit.create(TeamsServiceRetrofit::class.java)
        retrieveTeams("ALL")
    }

    private fun retrieveTeams(conference: String) {
        retrofitService.teams(conference).enqueue(object : Callback<List<Team>> {
            override fun onFailure(call: Call<List<Team>>, t: Throwable) {
                errorObservable.onNext(t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Team>>, response: Response<List<Team>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        teamsObservable.onNext(it)
                    }
                }
            }
        })
    }

}