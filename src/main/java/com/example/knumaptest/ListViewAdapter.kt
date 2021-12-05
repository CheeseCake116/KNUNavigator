package com.example.knumaptest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListViewAdapter(val context: Context, val ContentList: ArrayList<ListViewItem>) :BaseAdapter() {
    override fun getCount(): Int = ContentList.size
    override fun getItem(position: Int): ListViewItem = ContentList[position]
    override fun getItemId(position: Int): Long = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = LayoutInflater.from(parent?.context).inflate(R.layout.main_class_listitem, null)

        val timeA = view.findViewById<TextView>(R.id.classtimeA)
        val title = view.findViewById<TextView>(R.id.classTitle)
        val time = view.findViewById<TextView>(R.id.classtime)
        val location = view.findViewById<TextView>(R.id.classLocation)


        val content = ContentList[position]
        timeA.text = content.timeA
        title.text = content.title
        time.text = content.time
        location.text = content.location

        return view
    }
}