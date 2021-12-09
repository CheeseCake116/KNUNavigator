package com.fantastic4.knumap

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlin.collections.ArrayList

class AutoSuggestAdapter(context: Context, private var resource: Int, items: ArrayList<String>) : ArrayAdapter<String>(context, resource, items), Filterable {
    private val items: ArrayList<String> = ArrayList()
    private val suggestions: ArrayList<String> = ArrayList()
    var customLayoutHandler: ((View, String) -> Unit)? = null
    var noSuggestionsHandler: (() -> Unit)? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var v : View
        v = if(convertView == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(resource, parent, false)
        } else convertView

        if(suggestions.size > 0 && position < suggestions.size) {
            val item: String = suggestions[position]
            if(v is TextView) v.text = item
            else customLayoutHandler?.invoke(v, item)
        } else noSuggestionsHandler?.invoke()

        return v
    }

    override fun add(item: String?) {
        if(item != null) super.add(item)
        if(item != null && !items.contains(item)) items.add(item)
    }

    override fun addAll(vararg items: String) {
        super.addAll(*items)
        this.items.addAll(items)
    }

    override fun addAll(collection: MutableCollection<out String>) {
        super.addAll(collection)
        this.items.addAll(collection)
    }

    fun clearItems() {
        items.clear()
    }

    fun setData(new: List<String>) {
        items.clear()
        items.addAll(new)
    }

    override fun getCount(): Int {
        return suggestions.size
    }

    override fun getItem(position: Int): String? {
        return suggestions[position]
    }

    override fun getFilter(): Filter = ItemFilter()

    inner class ItemFilter(): Filter() {
        private val sync = object {}
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()

            if(constraint != null) {
                suggestions.clear()
                items.forEach { item ->
                    if(item.contains(constraint)) {
                        suggestions.add(item)
                        Log.e("suggestions", suggestions.toString())
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
                filterList.forEach { item -> add(item) }
                notifyDataSetChanged()
            } else noSuggestionsHandler?.invoke()
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return resultValue?.toString() ?: ""
        }
    }
}
