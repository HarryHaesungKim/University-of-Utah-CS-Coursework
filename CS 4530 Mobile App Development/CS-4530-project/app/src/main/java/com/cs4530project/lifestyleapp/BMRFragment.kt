package com.cs4530project.lifestyleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

class BMRFragment : Fragment() {

    private val mLifestyleViewModel: LifestyleViewModel by activityViewModels()

    private var tvBMR: TextView? = null
    private var tvDTCI: TextView? = null

    private val observer: Observer<CalorieData?> =
        Observer { calorieData ->
            if (calorieData != null) {
                tvBMR!!.text = String.format("%,.2f calories/day", calorieData.bmr)
                tvDTCI!!.text = String.format("%,.2f calories/day", calorieData.dtci)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bmr, container, false)
        tvBMR = view.findViewById(R.id.tv_bmr_data) as TextView
        tvDTCI = view.findViewById(R.id.tv_target_calorie_data) as TextView
        mLifestyleViewModel.calories.observe(viewLifecycleOwner, observer)
        return view
    }
}