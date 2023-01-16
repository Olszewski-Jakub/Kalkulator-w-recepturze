package com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.jakubolszewski.kalkulatorwrecepturzedoz.R

internal class VitaminAGridAdapter(
    private val resultsList: List<VitaminAGridModel>,
    private val context: Context
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var main_vit_TV: TextView
    private lateinit var main_vit2_TV: TextView
    private lateinit var mass_TV: TextView
    private lateinit var volume_TV: TextView
    private lateinit var drops_TV: TextView
    private lateinit var massunits_TV: TextView
    private lateinit var howMuchTosell_TV: TextView


    override fun getCount(): Int {
        return resultsList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.viatmin_a_gridview_item, null)
        }
        main_vit_TV = convertView!!.findViewById(R.id.textView_mainVit)
        main_vit2_TV = convertView!!.findViewById(R.id.textView_mainVit2)
        mass_TV = convertView!!.findViewById(R.id.textView_calculation_mass)
        volume_TV = convertView!!.findViewById(R.id.textView_calculation_volume)
        drops_TV = convertView!!.findViewById(R.id.textView_calculation_drops)
        massunits_TV = convertView!!.findViewById(R.id.textView_calculation_massunits)
        howMuchTosell_TV = convertView!!.findViewById(R.id.textView_howMuchToSell)

        main_vit_TV.text = resultsList[position].main_vit
        main_vit2_TV.text = resultsList[position].main_vit2
        mass_TV.text = resultsList[position].mass
        volume_TV.text = resultsList[position].volume
        drops_TV.text = resultsList[position].drops
        massunits_TV.text = resultsList[position].massunits
        howMuchTosell_TV.text = resultsList[position].howMuchTosell


        return convertView
    }
}
