@file:Suppress("DEPRECATION")

package com.jakubolszewski.kalkulatorwrecepturzedoz.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.GridRVAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.GridViewModal
import com.jakubolszewski.kalkulatorwrecepturzedoz.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private lateinit var gridView: GridView
    private lateinit var substancesList: ArrayList<GridViewModal>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        val buttonState = ArrayList<Boolean>()
        buttonState.add(prefs.getBoolean("witamina_A", true))
        buttonState.add(prefs.getBoolean("Witamina_E", true))
        buttonState.add(prefs.getBoolean("Witamina_A_D3", true))
        buttonState.add(prefs.getBoolean("Devicap", true))
        buttonState.add(prefs.getBoolean("Spirytus", true))
        buttonState.add(prefs.getBoolean("Olejki", true))
        buttonState.add(prefs.getBoolean("Oleje", true))

        gridView = view.findViewById(R.id.grid)
        substancesList = ArrayList()

        substancesList.add(
            GridViewModal(
                substance = "Witamina A",
                icon = R.drawable.ic_android_black_24dp
            )
        )
        substancesList.add(
            GridViewModal(
                substance = "Witamina E",
                icon = R.drawable.ic_android_black_24dp
            )
        )
        substancesList.add(
            GridViewModal(
                substance = "Witamina A+D3",
                icon = R.drawable.ic_android_black_24dp
            )
        )
        substancesList.add(
            GridViewModal(
                substance = "Devicap",
                icon = R.drawable.ic_android_black_24dp
            )
        )
        substancesList.add(
            GridViewModal(
                substance = "Spirytus",
                icon = R.drawable.ic_android_black_24dp
            )
        )
        substancesList.add(
            GridViewModal(
                substance = "Olejki",
                icon = R.drawable.ic_android_black_24dp
            )
        )
        substancesList.add(
            GridViewModal(
                substance = "Oleje",
                icon = R.drawable.ic_android_black_24dp
            )
        )
        val menuAdapter = context?.let { GridRVAdapter(courseList = substancesList, context = it) }

        gridView.adapter = menuAdapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

            Toast.makeText(
                context, position.toString(),
                Toast.LENGTH_SHORT
            ).show()

            var fragment: Fragment? = null


            when (position) {
                0 -> if (isActive(position = 0, state = buttonState)) {
                    fragment = VitaminAFragment()
                }
                1 -> if (isActive(position = 1, state = buttonState)) {
                    fragment = VitaminAFragment()
                }
                2 -> if (isActive(position = 2, state = buttonState)) {
                    fragment = VitaminAFragment()
                }
                3 -> if (isActive(position = 3, state = buttonState)) {
                    fragment = VitaminAFragment()
                }
                4 -> if (isActive(position = 4, state = buttonState)) {
                    fragment = VitaminAFragment()
                }
                5 -> if (isActive(position = 5, state = buttonState)) {
                    fragment = VitaminAFragment()
                }
                6 -> if (isActive(position = 6, state = buttonState)) {
                    fragment = VitaminAFragment()
                }
            }

            val transaction = parentFragmentManager.beginTransaction()
            if (fragment != null) {
                transaction.replace(R.id.container, fragment)
            } else {
                Toast.makeText(
                    context,
                    "Funkcja jest w tej chwili nie dostępna\nZa utrudnienia przepraszamy",
                    Toast.LENGTH_SHORT
                ).show()
            }
            transaction.commit()
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun isActive(position: Int, state: ArrayList<Boolean>): Boolean {
        if (state[position]) {
            return true
        }
        return false
    }
}