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
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAD3GridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAD3GridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.R
import com.jakubolszewski.kalkulatorwrecepturzedoz.calculations.VitaminAD3Calculations
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAD3Model

private lateinit var dbHelper: DBHelper


/**
 * A simple [Fragment] subclass.
 * Use the [VItaminAplusD3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VItaminAplusD3Fragment : Fragment() {
    private lateinit var gridView: GridView
    private lateinit var resultList: ArrayList<VitaminAD3GridModel>
    private lateinit var calcButton: Button
    private lateinit var editText: EditText
    private lateinit var backImgView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_v_itamin_aplus_d3, container, false)

        dbHelper = activity?.let { DBHelper(it) }!!
        var vitAD3List: ArrayList<VitAD3Model> = ArrayList()
        vitAD3List = dbHelper.getAllVitAD3()

        calcButton = view.findViewById(R.id.button_calc)
        editText = view.findViewById(R.id.editText_amount)
        backImgView = view.findViewById(R.id.imageView_arrow)

        gridView = view.findViewById(R.id.grid_vit_a_d3)
        resultList = ArrayList()
        val unitsGroup: MaterialButtonToggleGroup =
            view.findViewById(R.id.toggle_button_group_units)
        var unit: Int = -1

        unitsGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_vit_a_d3_g -> unit = 0
                    R.id.button_vit_a_d3_ml -> unit = 1
                    R.id.button_vit_a_d3_krople -> unit = 2
                }
            }
        }

        calcButton.setOnClickListener { _ ->
            //Check if all fields are filled
            if (editText.text.isNotBlank()) {
                val amount: Double = editText.text.toString().toDouble()

                Toast.makeText(context, unit.toString(), Toast.LENGTH_SHORT).show()
                resultList.clear()
                val Vitamins: Map<String, VitaminAD3GridModel> = VitaminAD3Calculations(
                    units = unit,
                    amount = amount,
                    vitAD3List = vitAD3List
                ).calculate()

                Vitamins["Vit"]?.let {
                    resultList.add(
                        it
                    )
                }

                val menuAdapter =
                    context?.let { VitaminAD3GridAdapter(resultsList = resultList, context = it) }

                gridView.adapter = menuAdapter
            }
        }
        backImgView.setOnClickListener {
            findNavController().navigate(R.id.action_VItaminAplusD3Fragment_to_homeFragment)
        }
        return view
    }

}
