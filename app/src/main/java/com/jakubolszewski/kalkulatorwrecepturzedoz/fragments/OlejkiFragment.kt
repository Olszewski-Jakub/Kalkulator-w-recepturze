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
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.OlejkiGridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.OlejkiGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.R
import com.jakubolszewski.kalkulatorwrecepturzedoz.calculations.OlejkiCalculations
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.OlejkiModel

class OlejkiFragment : Fragment() {
    private lateinit var dbHelper: DBHelper
    private lateinit var gridView: GridView
    private lateinit var resultList: ArrayList<OlejkiGridModel>
    private lateinit var calcButton: Button
    private lateinit var editText: EditText
    private lateinit var backImgView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_olejki, container, false)

        dbHelper = activity?.let { DBHelper(it) }!!
        var olejkiList: ArrayList<OlejkiModel> = ArrayList()
        olejkiList = dbHelper.getAllOlejki()

        calcButton = view.findViewById(R.id.button_calc)
        editText = view.findViewById(R.id.editText_amount)
        backImgView = view.findViewById(R.id.imageView_arrow)

        gridView = view.findViewById(R.id.grid_vit_a)
        resultList = ArrayList()

        val companyGroup: MaterialButtonToggleGroup =
            view.findViewById(R.id.toggle_button_group_type)
        val unitsGroup: MaterialButtonToggleGroup =
            view.findViewById(R.id.toggle_button_group_units)
        var type: Int = -1
        var unit: Int = -1

        companyGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_mint -> {
                        type = 0
                    }

                    R.id.button_lavender -> {
                        type = 1
                    }

                    R.id.button_eucalyptus -> {
                        type = 2
                    }
                }
            }
        }
        unitsGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_olejki_g -> unit = 0
                    R.id.button_olejki_ml -> unit = 1
                    R.id.button_olejki_krople -> unit = 2
                }
            }
        }

        //Start the calculations with specified parameters on button click
        calcButton.setOnClickListener { _ ->
            //Check if all fields are filled
            if (editText.text.isNotBlank()) {
                val amount: Double = editText.text.toString().toDouble()

                Toast.makeText(context, type.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, unit.toString(), Toast.LENGTH_SHORT).show()
                resultList.clear()
                val Olejki: Map<String, OlejkiGridModel> = OlejkiCalculations(
                    type = type,
                    units = unit,
                    amount = amount,
                    olejkiList = olejkiList
                ).calculate()

                Olejki["olejek"]?.let {
                    resultList.add(
                        it
                    )
                }

                val menuAdapter =
                    context?.let { OlejkiGridAdapter(resultsList = resultList, context = it) }

                gridView.adapter = menuAdapter
            }


        }
        //Go back to main menu
        backImgView.setOnClickListener {
            findNavController().navigate(R.id.action_olejkiFragment_to_homeFragment)
        }

        return view
    }

}