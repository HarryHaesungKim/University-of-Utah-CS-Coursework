package com.cs4530project.lifestyleapp

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import java.lang.ClassCastException

class ProfileFragment : Fragment(), View.OnClickListener {

    private var tvName: TextView? = null
    private var tvAge: TextView? = null
    private var tvCity: TextView? = null
    private var tvCountry: TextView? = null
    private var tvFeet: TextView? = null
    private var tvInches: TextView? = null
    private var tvWeight: TextView? = null
    private var tvSex: TextView? = null
    private var tvActivityLevel: TextView? = null
    private var ivThumbnail: ImageView? = null

    private var buttonUpdateProfile: Button? = null

    private var updateRequest: RequestUpdateInterface? = null

    private val mLifestyleViewModel: LifestyleViewModel by activityViewModels()

    private val flowObserver: Observer<UserData?> =
        Observer { userData -> // Update the UI if this data variable changes
            if (userData != null) {
                tvName!!.text = userData.name
                tvAge!!.text = userData.age.toString()
                tvCity!!.text = userData.city
                tvCountry!!.text = userData.country
                tvFeet!!.text = userData.feet.toString()
                tvInches!!.text = userData.inches.toString()
                tvWeight!!.text = userData.weight.toString()
                tvSex!!.text = userData.sex
                tvActivityLevel!!.text = userData.activityLevel

                // display the profile pic
                if (userData.imageFilePath != null) {
                    val thumbnailImage = BitmapFactory.decodeFile(userData.imageFilePath)
                    if (thumbnailImage != null)
                        ivThumbnail!!.setImageBitmap(thumbnailImage)
                }
            }
        }

    interface RequestUpdateInterface {
        fun requestToUpdate()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateRequest = try {
            context as RequestUpdateInterface
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement ProfileFragment.RequestUpdateInterface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // get the TextViews and ImageView that will display user data
        tvName = view.findViewById(R.id.tv_name_data) as TextView
        tvAge = view.findViewById(R.id.tv_age_data) as TextView
        tvCity = view.findViewById(R.id.tv_city_data) as TextView
        tvCountry = view.findViewById(R.id.tv_country_data) as TextView
        tvFeet = view.findViewById(R.id.tv_feet_data) as TextView
        tvInches = view.findViewById(R.id.tv_inches_data) as TextView
        tvWeight = view.findViewById(R.id.tv_weight_data) as TextView
        tvSex = view.findViewById(R.id.tv_sex_data) as TextView
        tvActivityLevel = view.findViewById(R.id.tv_activity_level_data) as TextView
        ivThumbnail = view.findViewById(R.id.iv_profile_pic) as ImageView

        // get button and attach click listener
        buttonUpdateProfile = view.findViewById(R.id.button_update_profile) as Button
        buttonUpdateProfile!!.setOnClickListener(this)

        mLifestyleViewModel.user.observe(viewLifecycleOwner, flowObserver)

        return view
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_update_profile -> {
                updateRequest!!.requestToUpdate()
            }
        }
    }
}