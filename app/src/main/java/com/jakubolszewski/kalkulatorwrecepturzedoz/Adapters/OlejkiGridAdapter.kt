package com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.jakubolszewski.kalkulatorwrecepturzedoz.R

class OlejkiGridAdapter(
    private val resultsList: List<OlejkiGridModel>,
    private val context: Context
) :
//BaseAdapter is a class that is used to create a custom adapter for a listview
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var main_vit_TV: TextView
    private lateinit var main_vit2_TV: TextView
    private lateinit var mass_TV: TextView
    private lateinit var volume_TV: TextView
    private lateinit var drops_TV: TextView


    //This method returns the number of items in the list
    override fun getCount(): Int {
        return resultsList.size
    }

    //This method returns the item at a given position
    override fun getItem(position: Int): Any? {
        return null
    }

    //This method returns the item id at a given position
    override fun getItemId(position: Int): Long {
        return 0
    }

    //This method returns the view at a given position
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        //Get the current item from ListView if layoutInflater is not null
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        //Inflate the layout for each list row if convertView is null
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.olejki_gridview_item, null)
        }
        //Get textviews from the layout
        main_vit_TV = convertView!!.findViewById(R.id.textView_mainVit)
        main_vit2_TV = convertView.findViewById(R.id.textView_mainVit2)
        mass_TV = convertView.findViewById(R.id.textView_calculation_mass)
        volume_TV = convertView.findViewById(R.id.textView_calculation_volume)
        drops_TV = convertView.findViewById(R.id.textView_calculation_drops)

        //Set text for textviews
        main_vit_TV.text = resultsList[position].main_olejek
        main_vit2_TV.text = resultsList[position].main_olejke2
        mass_TV.text = resultsList[position].mass
        volume_TV.text = resultsList[position].volume
        drops_TV.text = resultsList[position].drops



        return convertView
    }
}