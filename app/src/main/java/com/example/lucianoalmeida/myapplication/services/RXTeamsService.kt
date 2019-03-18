package com.example.lucianoalmeida.myapplication.services

import com.example.lucianoalmeida.myapplication.model.Team
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RXTeamsService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://nba-teams-3306.getsandbox.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var retrofitService: TeamsServiceRetrofit

    init {
        retrofitService = retrofit.create(TeamsServiceRetrofit::class.java)
        retrieveTeams("ALL")
    }

    fun retrieveTeams(conference: String): Single<List<Team>> {
        return RetrofitObservable(retrofitService.teams(conference))

    }

}

class RetrofitObservable<T>(val call: Call<T>): Single<T>(), Disposable {

    override fun subscribeActual(observer: SingleObserver<in T>) {
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                observer.onError(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        observer.onSuccess(it)
                    }
                }
            }
        })
    }

    override fun isDisposed(): Boolean {
        return call.isCanceled || call.isExecuted;
    }

    override fun dispose() {
        call.cancel()
    }
}