package com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.jakubolszewski.kalkulatorwrecepturzedoz.R

internal class GridRVAdapter(

    private val courseList: List<GridViewModal>,
    private val context: Context,
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var substanceTV: TextView
    private lateinit var iconIV: ImageView

    override fun getCount(): Int {
        return courseList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val columns = prefs.getInt("grid_columns", 2)
        if (convertView == null) {

            when (columns) {
                1 -> convertView = layoutInflater!!.inflate(R.layout.gridview_item_1, null)
                2 -> convertView = layoutInflater!!.inflate(R.layout.gridview_item_2, null)
                3 -> convertView = layoutInflater!!.inflate(R.layout.gridview_item_3, null)
                4 -> convertView = layoutInflater!!.inflate(R.layout.gridview_item_4, null)
                else -> convertView = layoutInflater!!.inflate(R.layout.gridview_item_2, null)
            }


        }
        iconIV = convertView!!.findViewById(R.id.icon)
        substanceTV = convertView.findViewById(R.id.substance)
        iconIV.setImageResource(courseList.get(position).icon)
        substanceTV.text = courseList.get(position).substance
        return convertView
    }
}
