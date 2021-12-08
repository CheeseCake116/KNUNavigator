package com.fantastic4.knumap

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

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

        val directBtn = view.findViewById<ImageButton>(R.id.directBtn)
        directBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("location",location.text)
            context.startActivity(intent)

        })
        return view
    }
}