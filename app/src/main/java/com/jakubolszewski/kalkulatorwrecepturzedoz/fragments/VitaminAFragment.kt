package com.jakubolszewski.kalkulatorwrecepturzedoz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.GridRVAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.GridViewModal
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModal
import com.jakubolszewski.kalkulatorwrecepturzedoz.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    private lateinit var resultList: ArrayList<VitaminAGridModal>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_vitamin_a, container, false)

        gridView = view.findViewById(R.id.grid_vit_a)
        resultList = ArrayList()
        resultList.add(
            VitaminAGridModal(
                main_vit = "test",
                main_vit2 = "test",
                mass = "test",
                volume = "test",
                drops = "test",
                massunits = "test",
                howMuchTosell = "test"
            )
        )

        resultList.add(
            VitaminAGridModal(
                main_vit = "test",
                main_vit2 = "test",
                mass = "test",
                volume = "test",
                drops = "test",
                massunits = "test",
                howMuchTosell = "test"
            )
        )
        resultList.add(
            VitaminAGridModal(
                main_vit = "test",
                main_vit2 = "test",
                mass = "test",
                volume = "test",
                drops = "test",
                massunits = "test",
                howMuchTosell = "test"
            )
        )

        val menuAdapter =
            context?.let { VitaminAGridAdapter(resultsList = resultList, context = it) }

        gridView.adapter = menuAdapter

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