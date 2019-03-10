package com.example.lucianoalmeida.myapplication

import android.content.Context
import android.database.DataSetObserver
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.ListFragment
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.TextView
import com.example.lucianoalmeida.myapplication.model.Team
import com.example.lucianoalmeida.myapplication.services.TeamsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity: AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext get() =  Dispatchers.Main

    val service: TeamsService = TeamsService()
    lateinit var eastTeamsAdapter: TeamsListAdapter
    lateinit var westTeamsAdapter: TeamsListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        eastTeamsAdapter = TeamsListAdapter(emptyList(), baseContext)
        westTeamsAdapter = TeamsListAdapter(emptyList(), baseContext)
        viewPager.adapter = TabsPagerAdapter(supportFragmentManager, eastTeamsAdapter, westTeamsAdapter)

        launch(Dispatchers.IO) {
            val teams = service.teams("ALL")

            withContext(Dispatchers.Main) {
                updateViews(teams)
            }
        }
    }

    private fun updateViews(teams: List<Team>) {
        eastTeamsAdapter.teams = teams.filter { it.conference == "EAST" }
        westTeamsAdapter.teams = teams.filter { it.conference == "WEST" }
        eastTeamsAdapter.notifyDataSetChanged()
        westTeamsAdapter.notifyDataSetChanged()
    }

    class TeamsListAdapter(var teams: List<Team>, val context: Context): BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val team = teams[position]
            val textView = TextView(context)
            textView.text = team.city + " " + team.name
            return textView
        }

        override fun getItem(position: Int): Any {
            return teams[position]
    }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return teams.size
        }

    }

    class TabsPagerAdapter(val fm: FragmentManager?,
                           val eastTeamsAdapter: TeamsListAdapter,
                           val westTeamsAdapter: TeamsListAdapter) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(i: Int): Fragment {
            val fragment = ListFragment()
            fragment.listAdapter = if (i == 0) eastTeamsAdapter else westTeamsAdapter
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) "East" else "West"
        }
    }

}

