package com.example.lucianoalmeida.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.ListFragment
import android.widget.ArrayAdapter
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
    var adapterList: ArrayAdapter<Team>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = TabsPagerAdapter(supportFragmentManager)

        launch(Dispatchers.IO) {
            val teams = service.teams("ALL")
            println(teams)
            withContext(Dispatchers.Main) {
                // Update UI
            }
        }
    }

    class TabsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(i: Int): Fragment {
            return ListFragment()
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) "East" else "West"
        }
    }

}

