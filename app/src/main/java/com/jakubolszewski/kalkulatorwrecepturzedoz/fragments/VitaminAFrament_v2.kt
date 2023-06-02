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
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.R
import com.jakubolszewski.kalkulatorwrecepturzedoz.calculations.VitaminACalculations
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var dbHelper: DBHelper

class VitaminAFrament_v2 : Fragment() {
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
    private lateinit var calcButton: Button
    private lateinit var editText: EditText
    private lateinit var backImgView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_vitamin_a_frament_v2, container, false)

        dbHelper = activity?.let { DBHelper(it) }!!
        var vitAList: ArrayList<VitAModel> = ArrayList()
        vitAList = dbHelper.getAllVitA()

        calcButton = view.findViewById(R.id.button_calc)
        editText = view.findViewById(R.id.editText_amount)
        backImgView = view.findViewById(R.id.imageView_arrow)

        gridView = view.findViewById(R.id.grid_vit_a)
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
                        val btn_g: MaterialButton = view.findViewById(R.id.button_vit_a_liq_g)
                        val btn_ml: MaterialButton = view.findViewById(R.id.button_vit_a_liq_ml)
                        btn_g.visibility = View.VISIBLE
                        btn_ml.visibility = View.VISIBLE
                        company = 0
                    }

                    R.id.button_medana -> {
                        val btn_g: MaterialButton = view.findViewById(R.id.button_vit_a_liq_g)
                        val btn_ml: MaterialButton = view.findViewById(R.id.button_vit_a_liq_ml)
                        btn_g.visibility = View.VISIBLE
                        btn_ml.visibility = View.VISIBLE
                        company = 1
                    }

                    R.id.button_fagron -> {
                        company = 2
                        val btn_g: MaterialButton = view.findViewById(R.id.button_vit_a_liq_g)
                        val btn_ml: MaterialButton = view.findViewById(R.id.button_vit_a_liq_ml)
                        val btn_jm: MaterialButton = view.findViewById(R.id.button_vit_a_jm)
                        btn_g.visibility = View.GONE
                        btn_ml.visibility = View.GONE
                        btn_jm.isChecked = true
                    }
                }
            }
        }
        unitsGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_vit_a_liq_g -> unit = 0
                    R.id.button_vit_a_liq_ml -> unit = 2
                    R.id.button_vit_a_jm -> unit = 1
                }
            }
        }

        //Start the calculations with specified parameters on button click
        calcButton.setOnClickListener { _ ->
            //Check if all fields are filled
            if (editText.text.isNotBlank()) {
                val amount: Double = editText.text.toString().toDouble()

                Toast.makeText(context, company.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, unit.toString(), Toast.LENGTH_SHORT).show()
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
            findNavController().navigate(R.id.action_vitaminAFrament_v2_to_homeFragment)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VitaminAFrament_v2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VitaminAFrament_v2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}