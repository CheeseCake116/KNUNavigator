package com.fantastic4.knumap

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView

class Rest_LVAdapter(val context: Context, val ContentList: ArrayList<Rest_LVItem>) :BaseAdapter() {
    override fun getCount(): Int = ContentList.size
    override fun getItem(position: Int): Rest_LVItem = ContentList[position]
    override fun getItemId(position: Int): Long = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = LayoutInflater.from(parent?.context).inflate(R.layout.main_rest_listitem, null)

        val title = view.findViewById<TextView>(R.id.restTitle)
        val time = view.findViewById<TextView>(R.id.restTime)
        val menu = view.findViewById<TextView>(R.id.restMenu)


        val content = ContentList[position]
        time.text = content.time
        title.text = content.title
        menu.text = content.menu

        val directBtn2 = view.findViewById<ImageButton>(R.id.directBtn2)
        directBtn2.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        })

        return view
    }
}