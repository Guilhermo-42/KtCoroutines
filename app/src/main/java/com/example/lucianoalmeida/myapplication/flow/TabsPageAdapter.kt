package com.example.lucianoalmeida.myapplication.flow

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.ListFragment


class TabsPagerAdapter(fm: FragmentManager?,
                       private val adapters: List<TeamsListAdapter>
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = adapters.size

    override fun getItem(position: Int): Fragment {
        val fragment = ListFragment()
        fragment.listAdapter = adapters[position]
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) "East" else "West"
    }

}