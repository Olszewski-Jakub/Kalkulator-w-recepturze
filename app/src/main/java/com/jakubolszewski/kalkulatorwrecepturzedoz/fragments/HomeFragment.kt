@file:Suppress("DEPRECATION")

package com.jakubolszewski.kalkulatorwrecepturzedoz.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.GridRVAdapter
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.GridViewModal
import com.jakubolszewski.kalkulatorwrecepturzedoz.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private lateinit var gridView: GridView
    private lateinit var substancesList: ArrayList<GridViewModal>
    private lateinit var settings: ImageView

    //Creates the view of the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        settings = view.findViewById(R.id.settings)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        //Gets the value of the key "substances" from the shared preferences to check if button is turned on or off
        val buttonState = ArrayList<Boolean>()
        buttonState.add(prefs.getBoolean("witamina_A", true))
        buttonState.add(prefs.getBoolean("Witamina_E", true))
        buttonState.add(prefs.getBoolean("Witamina_A_D3", true))
        buttonState.add(prefs.getBoolean("Devicap", true))
        buttonState.add(prefs.getBoolean("Spirytus", true))
        buttonState.add(prefs.getBoolean("Olejki", true))
        buttonState.add(prefs.getBoolean("Oleje", true))

        //Add the substances to the list then pass it to the adapter
        gridView = view.findViewById(R.id.grid)
        gridView.numColumns = prefs.getInt("grid_columns", 2)
        substancesList = ArrayList()

        substancesList.add(
            GridViewModal(
                substance = "Witamina A",
                icon = R.drawable.vitamin_a
            )
        )
        substancesList.add(
            GridViewModal(
                substance = "Witamina E",
                icon = R.drawable.vitamin_e
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
//        substancesList.add(
//            GridViewModal(
//                substance = "Witamia A v2",
//                icon = R.drawable.ic_android_black_24dp
//            )
//        )
        val menuAdapter = context?.let { GridRVAdapter(courseList = substancesList, context = it) }

        //Set the adapter to the grid view
        gridView.adapter = menuAdapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

            Toast.makeText(
                context, position.toString(),
                Toast.LENGTH_SHORT
            ).show()
            var active: Boolean = false
            //Checks if the button is turned on or off
            when (position) {
                //Vitamin A
                0 -> if (isActive(position = 0, state = buttonState)) {
                    findNavController().navigate(R.id.action_homeFragment_to_vitaminAFrament_v2)
                    active = true
                }
                //Vitamin E
                1 -> if (isActive(position = 1, state = buttonState)) {
                    findNavController().navigate(R.id.action_homeFragment_to_vitaminEFragment)
                    active = true
                }
                //Vitamin A+D3
                2 -> if (isActive(position = 2, state = buttonState)) {
                    findNavController().navigate(R.id.action_homeFragment_to_VItaminAplusD3Fragment)
                    active = true
                }
                //Devicap
                3 -> if (isActive(position = 3, state = buttonState)) {
                    findNavController().navigate(R.id.action_homeFragment_to_devicapFragment)
                    active = true
                }
                //Spirytus
                4 -> if (isActive(position = 4, state = buttonState)) {
                    findNavController().navigate(R.id.action_homeFragment_to_alcoholFragment)
                    active = true
                }
                //Olejki
                5 -> if (isActive(position = 5, state = buttonState)) {
                    findNavController().navigate(R.id.action_homeFragment_to_olejkiFragment)
                    active = true
                }
                //Oleje
                6 -> if (isActive(position = 6, state = buttonState)) {
                    findNavController().navigate(R.id.action_homeFragment_to_olejeFragment)
                    active = true
                }

//                7 -> findNavController().navigate(R.id.action_homeFragment_to_vitaminAFrament_v2)
            }
            //Replace the fragment
            val transaction = parentFragmentManager.beginTransaction()
            if (!active) {
                Toast.makeText(
                    context,
                    "Funkcja jest w tej chwili nie dostępna\nZa utrudnienia przepraszamy",
                    Toast.LENGTH_SHORT
                ).show()
            }
//            transaction.commit()
        }

        //Go back to main menu
        settings.setOnClickListener { view ->
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }


        return view
    }


    //Functions checks if button is active
    private fun isActive(position: Int, state: ArrayList<Boolean>): Boolean {
        if (state[position]) {
            return true
        }
        return false
    }
}