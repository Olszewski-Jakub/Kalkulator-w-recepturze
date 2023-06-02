package com.jakubolszewski.kalkulatorwrecepturzedoz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.R
import com.jakubolszewski.kalkulatorwrecepturzedoz.calculations.VitaminACalculations
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var dbHelper: DBHelper

class VitaminAFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var gridView: GridView
    private lateinit var resultList: ArrayList<VitaminAGridModel>
    private lateinit var companySpinner: Spinner
    private lateinit var unitSpinner: Spinner
    private lateinit var calcButton: Button
    private lateinit var editText: EditText
    private lateinit var backImgView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_vitamin_a, container, false)

        dbHelper = activity?.let { DBHelper(it) }!!
        var vitAList: ArrayList<VitAModel> = ArrayList()
        vitAList = dbHelper.getAllVitA()

        companySpinner = view.findViewById(R.id.spinner_Company)
        unitSpinner = view.findViewById(R.id.spinner_unit)
        calcButton = view.findViewById(R.id.button_calc)
        editText = view.findViewById(R.id.editText_amount)
        backImgView = view.findViewById(R.id.imageView_arrow)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val companies: ArrayList<String> = ArrayList()
        companies.add("Wybierz")
        companies.add(vitAList.get(0).company)
        companies.add(vitAList.get(1).company)
        companies.add(vitAList.get(2).company)

        val units: ArrayList<String> = ArrayList()
        units.add("Wybierz")
        units.add("Vit. A liq (g)")
        units.add("Vit. A liq (ml)")
        units.add("Vit. A (j.m.)")


        val companyAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            companies
        )
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        companySpinner.adapter = companyAdapter

        val unitAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            units
        )
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        unitSpinner.adapter = unitAdapter

        gridView = view.findViewById(R.id.grid_vit_a)
        resultList = ArrayList()

        //Start the calculations with specified parameters on button click
        calcButton.setOnClickListener { view ->
            //Check if all fields are filled
            if (editText.text.isNotBlank()) {
                var amount: Double = editText.text.toString().toDouble()
                var company: Int = -1
                var unit: Int = -1

                when (companySpinner.selectedItem.toString()) {
                    "hasco" -> company = 0
                    "medana" -> company = 1
                    "fagron" -> company = 2
                }

                when (unitSpinner.selectedItem.toString()) {
                    units[1] -> unit = 0
                    units[2] -> unit = 2
                    units[3] -> unit = 1
                }

                resultList.clear()
                val Vitamins: Map<String, VitaminAGridModel> = VitaminACalculations(
                    company = company,
                    units = unit,
                    amount = amount,
                    vitAList = vitAList
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
                    context?.let { VitaminAGridAdapter(resultsList = resultList, context = it) }

                gridView.adapter = menuAdapter
            }


        }
        //Go back to main menu
        backImgView.setOnClickListener {
            findNavController().navigate(R.id.action_vitaminAFragment_to_homeFragment)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VitaminAFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}