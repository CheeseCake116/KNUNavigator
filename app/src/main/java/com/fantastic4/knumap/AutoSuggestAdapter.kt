package com.fantastic4.knumap

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlin.collections.ArrayList

class AutoSuggestAdapter(context: Context, private var resource: Int, var searchMapitems: ArrayList<String>) : ArrayAdapter<String>(context, resource, searchMapitems), Filterable {
    private val suggestions: ArrayList<String> = ArrayList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var v : View
        v = if(convertView == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(resource, parent, false)
        } else convertView

        if(suggestions.size > 0 && position < suggestions.size) {
            val item: String = suggestions[position]
            if(v is TextView) v.text = item
        }

        return v
    }

    override fun getCount(): Int {
        return searchMapitems.size
    }

    override fun getItem(position: Int): String? {
        return searchMapitems[position]
    }

    fun setData(newSearchItemList: List<String>) {
        searchMapitems.clear()
        searchMapitems.addAll(newSearchItemList)
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if(constraint != null) {
                    suggestions.clear()
                    searchMapitems.forEach { item ->
                        if(item.contains(constraint)) {
                            suggestions.add(item)
                            Log.e("suggestions", item)
                        }
                    }

                    filterResults.values = suggestions
                    filterResults.count = suggestions.size
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if(results != null && results.count > 0) {
                    var filterList: ArrayList<String> = results.values as ArrayList<String>
                    clear()
                    filterList.toArray().forEach { item -> add(item as String) }
                    notifyDataSetChanged()
                }
                else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}
