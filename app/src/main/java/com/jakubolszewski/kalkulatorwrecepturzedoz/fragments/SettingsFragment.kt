package com.jakubolszewski.kalkulatorwrecepturzedoz.fragments

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jakubolszewski.kalkulatorwrecepturzedoz.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var columnCountText:TextView
    private lateinit var appVersionTextView:TextView
    private lateinit var plusOne:ImageView
    private lateinit var minusOne:ImageView
    private lateinit var backImgView:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_settings, container, false)


        ///Initialize the elements of the view
        columnCountText = view.findViewById(R.id.number_of_columns)
        plusOne = view.findViewById(R.id.plus_one)
        minusOne = view.findViewById(R.id.minus_one)
        backImgView = view.findViewById(R.id.imageView_arrow)
        appVersionTextView = view.findViewById(R.id.version_textView)
        //Initialize the shared preferences
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        //Get the value of the key "column_count" from the shared preferences
        columnCountText.text = prefs.getInt("grid_columns", 2).toString()

        //Set the onClickListener for the plusOne button to increase the number of columns in the grid
        plusOne.setOnClickListener(View.OnClickListener {
            var count = prefs.getInt("grid_columns", 2)
            if(count < 4){
                count++
                columnCountText.text = count.toString()
                prefs.edit().putInt("grid_columns", count).apply()
            }
        })

        //Set the onClickListener for the minusOne button to decrease the number of columns in the grid
        minusOne.setOnClickListener(View.OnClickListener {
            var count = prefs.getInt("grid_columns", 2)
            if(count > 1){
                count--
                columnCountText.text = count.toString()
                prefs.edit().putInt("grid_columns", count).apply()
            }
        })

        //Go back to main menu
        backImgView.setOnClickListener { view ->
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, HomeFragment())
            transaction.commit()
        }
        try {
            val pInfo: PackageInfo =
                requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            val version: String = pInfo.versionName
//            Toast.makeText(requireContext(), version, Toast.LENGTH_LONG).show()
            appVersionTextView.text = "Version: $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
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
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}