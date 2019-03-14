package com.example.lucianoalmeida.myapplication.flow

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.lucianoalmeida.myapplication.model.Team


class TeamsListAdapter(var teams: List<Team>, private val context: Context): BaseAdapter() {

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