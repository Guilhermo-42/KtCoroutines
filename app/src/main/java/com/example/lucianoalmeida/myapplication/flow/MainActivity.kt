package com.example.lucianoalmeida.myapplication.flow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.lucianoalmeida.myapplication.R
import com.example.lucianoalmeida.myapplication.model.Team
import com.example.lucianoalmeida.myapplication.services.RXTeamsService
import com.example.lucianoalmeida.myapplication.services.TeamsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext get() = Dispatchers.IO

    private val service: TeamsService = TeamsService()
    private val rxService: RXTeamsService = RXTeamsService()

    private val eastTeamsAdapter: TeamsListAdapter = TeamsListAdapter(emptyList(), this)
    private val westTeamsAdapter: TeamsListAdapter = TeamsListAdapter(emptyList(), this)


    //Rx
    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable ()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = TabsPagerAdapter(
            supportFragmentManager,
            listOf(eastTeamsAdapter, westTeamsAdapter))


        rxService.retrieveTeams("ALL")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { teams, error ->
                error?.let {
                    onError()
                }
                teams.let {
                    updateViews(it)
                }
            }.run(disposable::add)

    }

    private fun onError() {
        Toast.makeText(this, "Erro", Toast.LENGTH_LONG).show()
    }

    private fun updateViews(teams: List<Team>) {
        eastTeamsAdapter.teams = teams.filter { it.conference == "EAST" }
        westTeamsAdapter.teams = teams.filter { it.conference == "WEST" }
        eastTeamsAdapter.notifyDataSetChanged()
        westTeamsAdapter.notifyDataSetChanged()
    }

    private fun fetchTeams() {
        launch {
            try {
                val teams = service.retrieveTeams("ALL")

                withContext(Dispatchers.Main) {
                    updateViews(teams)
                }
            } catch (e: Exception) {
                onError()
            }
        }
    }

}

