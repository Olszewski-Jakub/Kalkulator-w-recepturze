package com.jakubolszewski.kalkulatorwrecepturzedoz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminEGridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminEGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.R
import com.jakubolszewski.kalkulatorwrecepturzedoz.calculations.VitaminECalculation
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitEModel

private lateinit var dbHelper: DBHelper

class VitaminEFragment : Fragment() {


    private lateinit var gridView: GridView
    private lateinit var resultList: ArrayList<VitaminEGridModel>
    private lateinit var calcButton: Button
    private lateinit var editText: EditText
    private lateinit var backImgView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_vitamin_e, container, false)

        dbHelper = activity?.let { DBHelper(it) }!!
        var vitEList: ArrayList<VitEModel> = ArrayList()
        vitEList = dbHelper.getAllVitE()

        calcButton = view.findViewById(R.id.button_calc)
        editText = view.findViewById(R.id.editText_amount)
        backImgView = view.findViewById(R.id.imageView_arrow)

        gridView = view.findViewById(R.id.grid_vit_e)
        resultList = ArrayList()

        val companyGroup: MaterialButtonToggleGroup =
            view.findViewById(R.id.toggle_button_group_company)
        val unitsGroup: MaterialButtonToggleGroup =
            view.findViewById(R.id.toggle_button_group_units)
        var company: Int = -1
        var unit: Int = -1


        companyGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_hasco -> {
                        company = 0
                        val btn: MaterialButton = view.findViewById(R.id.button_vit_a_liq_ml)
                        btn.visibility = View.VISIBLE
                    }

                    R.id.button_medana -> {
                        company = 1
                        val btn: MaterialButton = view.findViewById(R.id.button_vit_a_liq_ml)
                        btn.visibility = View.VISIBLE
                    }

                    R.id.button_fagron -> {
                        company = 2
                        val btn: MaterialButton = view.findViewById(R.id.button_vit_a_liq_ml)
                        btn.visibility = View.GONE
                        val btn2: MaterialButton = view.findViewById(R.id.button_vit_a_liq_g)
                        btn2.isChecked = true
                    }
                }
            }
        }
        unitsGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_vit_a_liq_g -> unit = 0
                    R.id.button_vit_a_liq_ml -> unit = 1
                }
            }
        }


        calcButton.setOnClickListener { _ ->
            if (editText.text.isNotBlank()) {
                val amount: Double = editText.text.toString().toDouble()
                resultList.clear()
                val Vitamins: Map<String, VitaminEGridModel> = VitaminECalculation(
                    company = company, units = unit, amount = amount, vitEList = vitEList
                ).calculate()

                Vitamins["Vit1"]?.let {
                    resultList.add(
                        it
                    )
                }
                Vitamins["Vit2"]?.let {
                    resultList.add(
                        it
                    )
                }
                Vitamins["Vit3"]?.let {
                    resultList.add(
                        it
                    )
                }
                val menuAdapter =
                    context?.let { VitaminEGridAdapter(resultsList = resultList, context = it) }

                gridView.adapter = menuAdapter

            }


        }

        backImgView.setOnClickListener {
            findNavController().navigate(R.id.action_vitaminEFragment_to_homeFragment)
        }
        return view
    }

}
