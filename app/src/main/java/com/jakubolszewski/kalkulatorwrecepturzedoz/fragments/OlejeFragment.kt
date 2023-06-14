package com.jakubolszewski.kalkulatorwrecepturzedoz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.OlejGridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.OlejGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.R
import com.jakubolszewski.kalkulatorwrecepturzedoz.calculations.OlejeCalculations
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.OlejeModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAModel


private lateinit var dbHelper: DBHelper

class OlejeFragment : Fragment() {
    private lateinit var gridView: GridView
    private lateinit var resultList: ArrayList<OlejGridModel>
    private lateinit var calcButton: Button
    private lateinit var editText: EditText
    private lateinit var backImgView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_oleje, container, false)

        dbHelper = activity?.let { DBHelper(it) }!!
        var olejList: ArrayList<OlejeModel> = ArrayList()
        olejList = dbHelper.getAllOleje()

        calcButton = view.findViewById(R.id.button_calc)
        editText = view.findViewById(R.id.editText_amount)
        backImgView = view.findViewById(R.id.imageView_arrow)

        gridView = view.findViewById(R.id.grid_vit_a)
        resultList = ArrayList()

        val typeGroup: MaterialButtonToggleGroup =
            view.findViewById(R.id.toggle_button_group_type)
        val unitsGroup: MaterialButtonToggleGroup =
            view.findViewById(R.id.toggle_button_group_units)
        var type: Int = -1
        var unit: Int = -1

        typeGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_rapae -> type = 0
                    R.id.button_ricini -> type = 1

                }
            }
        }
        unitsGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_olej_g -> unit = 0
                    R.id.button_olej_ml -> unit = 1
                }
            }
        }
        val density_editText = view.findViewById(R.id.editText_density) as EditText

        calcButton.setOnClickListener { _ ->
            //Check if all fields are filled
            if (editText.text.isNotBlank()) {
                val amount: Double = editText.text.toString().toDouble()
                val density: Double =
                    if (density_editText.text.isNotBlank()) density_editText.text.toString()
                        .toDouble() else -1.0
                Toast.makeText(context, type.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, unit.toString(), Toast.LENGTH_SHORT).show()
                resultList.clear()
                val Oleje: Map<String, OlejGridModel> = OlejeCalculations(
                    type = type,
                    units = unit,
                    amount = amount,
                    densityGiven = density,
                    olejeList = olejList
                ).calculate()

                Oleje["olej"]?.let {
                    resultList.add(
                        it
                    )
                }
                val menuAdapter =
                    context?.let { OlejGridAdapter(resultsList = resultList, context = it) }

                gridView.adapter = menuAdapter
            }


        }
        //Go back to main menu
        backImgView.setOnClickListener {
            findNavController().navigate(R.id.action_olejeFragment_to_homeFragment)
        }
        return view
    }
}