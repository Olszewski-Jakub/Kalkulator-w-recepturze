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
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.DevicapGridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.DevicapGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.R
import com.jakubolszewski.kalkulatorwrecepturzedoz.calculations.DevicapCalculations
import com.jakubolszewski.kalkulatorwrecepturzedoz.calculations.VitaminACalculations
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.DevicapModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAModel

private lateinit var dbHelper: DBHelper

class DevicapFragment : Fragment() {
    private lateinit var gridView: GridView
    private lateinit var resultList: ArrayList<DevicapGridModel>
    private lateinit var calcButton: Button
    private lateinit var editText: EditText
    private lateinit var backImgView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_devicap, container, false)


        dbHelper = activity?.let { DBHelper(it) }!!
        var devicapList: ArrayList<DevicapModel> = ArrayList()
        devicapList = dbHelper.getAllDevicap()

        calcButton = view.findViewById(R.id.button_calc)
        editText = view.findViewById(R.id.editText_amount)
        backImgView = view.findViewById(R.id.imageView_arrow)

        gridView = view.findViewById(R.id.grid_devicap)
        resultList = ArrayList()

        val unitsGroup: MaterialButtonToggleGroup =
            view.findViewById(R.id.toggle_button_group_units)
        var unit: Int = -1

        unitsGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_devicap_g -> unit = 0
                    R.id.button_devicap_ml -> unit = 1
                    R.id.button_devicap_jm -> unit = 2
                    R.id.button_devicap_krople -> unit = 3
                }
            }
        }

        calcButton.setOnClickListener { _ ->
            //Check if all fields are filled
            if (editText.text.isNotBlank()) {
                val amount: Double = editText.text.toString().toDouble()

                Toast.makeText(context, unit.toString(), Toast.LENGTH_SHORT).show()
                resultList.clear()
                val Vitamins: Map<String, DevicapGridModel> = DevicapCalculations(
                    units = unit,
                    amount = amount,
                    devicapList = devicapList
                ).calculate()

                Vitamins["Vit1"]?.let {
                    resultList.add(
                        it
                    )
                }
                val menuAdapter =
                    context?.let { DevicapGridAdapter(resultsList = resultList, context = it) }

                gridView.adapter = menuAdapter
            }


        }
        //Go back to main menu
        backImgView.setOnClickListener {
            findNavController().navigate(R.id.action_devicapFragment_to_homeFragment)
        }
        return view
    }

}