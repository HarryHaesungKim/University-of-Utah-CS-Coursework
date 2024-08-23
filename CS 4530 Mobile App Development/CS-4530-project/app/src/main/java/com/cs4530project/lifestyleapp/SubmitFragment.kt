package com.cs4530project.lifestyleapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_LOW_POWER
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.slider.Slider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.ClassCastException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SubmitFragment : Fragment(), View.OnClickListener {

    private var etName: EditText? = null
    private var etCity: EditText? = null
    private var etCountry: EditText? = null

    private var sliderAge: Slider? = null
    private var sliderFeet: Slider? = null
    private var sliderInches: Slider? = null
    private var sliderWeight: Slider? = null

    private var tvAge: TextView? = null
    private var tvFeet: TextView? = null
    private var tvInches: TextView? = null
    private var tvWeight: TextView? = null

    private var spinSex: Spinner? = null
    private var spinActivityLevel: Spinner? = null

    private var buttonSubmit: Button? = null
    private var buttonCamera: Button? = null
    private var buttonLocation: Button? = null

    private var ivThumbnail: ImageView? = null
    private var thumbnailImage: Bitmap? = null
    private var imageFilePath: String? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    private var submitRequest: RequestSubmissionInterface? = null

    private val mLifestyleViewModel: LifestyleViewModel by activityViewModels()

    interface RequestSubmissionInterface {
        fun requestToSubmit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        submitRequest = try {
            context as RequestSubmissionInterface
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SubmitFragment.RequestSubmissionInterface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_submit, container, false)

        // Create location services client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    getLocationInfo()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    getLocationInfo()
                } else -> {
                    Toast.makeText(activity, "Location access denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

        etName = view.findViewById(R.id.et_name) as EditText
        etCity = view.findViewById(R.id.et_city) as EditText
        etCountry = view.findViewById(R.id.et_country) as EditText

        sliderAge = view.findViewById(R.id.slider_age) as Slider
        sliderAge!!.addOnChangeListener { _, value, _ ->
            tvAge!!.text = "${value.toInt()} years"
        }
        sliderFeet = view.findViewById(R.id.slider_feet) as Slider
        sliderFeet!!.addOnChangeListener { _, value, _ ->
            tvFeet!!.text = "${value.toInt()} feet"
        }
        sliderInches = view.findViewById(R.id.slider_inches) as Slider
        sliderInches!!.addOnChangeListener { _, value, _ ->
            tvInches!!.text = "${value.toInt()} inches"
        }
        sliderWeight = view.findViewById(R.id.slider_weight) as Slider
        sliderWeight!!.addOnChangeListener { _, value, _ ->
            tvWeight!!.text = "${value.toInt()} pounds"
        }

        tvAge = view.findViewById(R.id.tv_age_entry) as TextView
        tvAge!!.text = "${sliderAge!!.value.toInt()} years"
        tvFeet = view.findViewById(R.id.tv_feet_entry) as TextView
        tvFeet!!.text = "${sliderFeet!!.value.toInt()} feet"
        tvInches = view.findViewById(R.id.tv_inches_entry) as TextView
        tvInches!!.text = "${sliderInches!!.value.toInt()} inches"
        tvWeight = view.findViewById(R.id.tv_weight_entry) as TextView
        tvWeight!!.text = "${sliderWeight!!.value.toInt()} pounds"

        spinSex = view.findViewById(R.id.spinner_sex) as Spinner
        spinActivityLevel = view.findViewById(R.id.spinner_activity_level) as Spinner

        buttonLocation = view.findViewById(R.id.button_request_location) as Button
        buttonLocation!!.setOnClickListener(this)
        buttonSubmit = view.findViewById(R.id.button_submit) as Button
        buttonSubmit!!.setOnClickListener(this)
        buttonCamera = view.findViewById(R.id.button_pic) as Button
        buttonCamera!!.setOnClickListener(this)

        ivThumbnail = view.findViewById(R.id.iv_preview_pic) as ImageView

        val userData = mLifestyleViewModel.user.value
        if (userData != null) {
            etName!!.setText(userData.name)
            etCity!!.setText(userData.city)
            etCountry!!.setText(userData.country)
            sliderAge!!.value = userData.age.toFloat()
            sliderFeet!!.value = userData.feet.toFloat()
            sliderInches!!.value = userData.inches.toFloat()
            sliderWeight!!.value = userData.weight.toFloat()
            spinSex!!.setSelection((spinSex!!.adapter as ArrayAdapter<String>).getPosition(userData.sex))
            spinActivityLevel!!.setSelection(
                (spinActivityLevel!!.adapter as ArrayAdapter<String>).getPosition(
                    userData.activityLevel
                )
            )
            if (userData.imageFilePath != null) {
                thumbnailImage = BitmapFactory.decodeFile(userData.imageFilePath)
                if (thumbnailImage != null) {
                    ivThumbnail!!.setImageBitmap(thumbnailImage)
                }
                imageFilePath = userData.imageFilePath
            }
        }

        return view
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_submit -> {

                // get inputs from TextEdits
                val name = etName!!.text.toString()
                val age = sliderAge!!.value.toInt()
                val city = etCity!!.text.toString()
                val country = etCountry!!.text.toString()
                val feet = sliderFeet!!.value.toInt()
                val inches = sliderInches!!.value.toInt()
                val weight = sliderWeight!!.value.toInt()
                val sex = spinSex!!.selectedItem.toString()
                val activityLevel = spinActivityLevel!!.selectedItem.toString()
                val coordinates = getCityCoords(city, country)

                // verify that the user filled out all fields and took a photo
                if (name.isBlank()) {
                    Toast.makeText(activity, "Please enter your name!", Toast.LENGTH_SHORT).show()
                } else if (city.isBlank()) {
                    Toast.makeText(activity, "Please enter the city where you live!", Toast.LENGTH_SHORT).show()
                } else if (country.isBlank()) {
                    Toast.makeText(activity, "Please enter the country where you live!", Toast.LENGTH_SHORT).show()
                } else if (coordinates == null) {
                    Toast.makeText(activity, "Your location could not be recognized from the city and country you entered.", Toast.LENGTH_SHORT).show()
                } else if (imageFilePath.isNullOrBlank()) {
                    Toast.makeText(activity, "Please take a profile picture before submitting!", Toast.LENGTH_SHORT).show()

                // data is ready for submisison
                } else {
                    Toast.makeText(activity, "Thank you for filling out your profile!", Toast.LENGTH_SHORT).show()

                    val userData = UserData()
                    userData.name = name
                    userData.city = city
                    userData.country = country
                    userData.age = age
                    userData.feet = feet
                    userData.inches = inches
                    userData.weight = weight
                    userData.sex = sex
                    userData.activityLevel = activityLevel
                    userData.imageFilePath = imageFilePath
                    userData.latitude = coordinates.latitude
                    userData.longitude = coordinates.longitude

                    mLifestyleViewModel.setUserData(userData)

                    submitRequest!!.requestToSubmit()
                }
            }

            R.id.button_pic -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    cameraLauncher.launch(cameraIntent)
                } catch (ex: ActivityNotFoundException){
                    Toast.makeText(activity, "There was a problem launching the camera", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.button_request_location -> {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) -> {
                        getLocationInfo()
                    }
                    ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                        getLocationInfo()
                    }
                    else -> {
                        locationPermissionRequest.launch(arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION))
                    }
                }
            }
        }
    }

    private var cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val extras = result.data!!.extras
            thumbnailImage = extras!!["data"] as Bitmap?

            // open a file and write to it
            if (isExternalStorageWritable) {
                imageFilePath = saveImage(thumbnailImage)
                if (thumbnailImage != null) {
                    ivThumbnail!!.setImageBitmap(thumbnailImage)
                }
            } else {
                Toast.makeText(activity, "External storage not writable", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImage(finalBitmap: Bitmap?): String {
        val root = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val myDir = File("$root/saved_images")
        myDir.mkdirs()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val filename = "Thumbnail_$timeStamp.jpg"
        val file = File(myDir, filename)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            Toast.makeText(activity, "File saved!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save path to photo
        outState.putString("IMAGE_FILEPATH", imageFilePath)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        // restore photo if one was taken
        if (savedInstanceState != null) {
            imageFilePath = savedInstanceState.getString("IMAGE_FILEPATH")
            if (imageFilePath != null) {
                thumbnailImage = BitmapFactory.decodeFile(imageFilePath)
                if (thumbnailImage != null) {
                    ivThumbnail!!.setImageBitmap(thumbnailImage)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocationInfo() {
        fusedLocationClient.getCurrentLocation(PRIORITY_LOW_POWER, null).addOnSuccessListener { location : Location? ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                val geocoder = Geocoder(requireActivity())
                if (Build.VERSION.SDK_INT >= 33) {
                    val geocodeListener = Geocoder.GeocodeListener { addresses ->
                        etCity!!.setText(addresses[0].locality)
                        etCountry!!.setText(addresses[0].countryName)
                    }
                    geocoder.getFromLocation(lat, long, 1, geocodeListener)
                } else {
                    val addresses = geocoder.getFromLocation(lat, long, 1)
                    if (addresses != null) {
                        etCity!!.setText(addresses[0].locality)
                        etCountry!!.setText(addresses[0].countryName)
                    }
                }
            }
        }
    }

    private fun getCityCoords(city: String, country: String): LatLng? {
        try {
            val geocoder = Geocoder(requireActivity(), Locale.getDefault())

            // return null if the geocoder cannot find an actual location based on the input
            val addressListFromCity = geocoder.getFromLocationName(city, 1)
            if (addressListFromCity == null || addressListFromCity.isEmpty() || addressListFromCity[0] == null)
                return null
            val addressListFromCountry = geocoder.getFromLocationName(country, 1)
            if (addressListFromCountry == null || addressListFromCountry.isEmpty() || addressListFromCountry[0] == null)
                return null
            val addressList = geocoder.getFromLocationName("$city, $country", 1)
            if (addressList == null || addressList.isEmpty() || addressList[0] == null)
                return null

            // get the latitude and longitude coordinates of the user's city
            else {
                val loc = addressList[0]
                return LatLng(loc.latitude, loc.longitude)
            }
        } catch (e: IOException) {
            return null
        }
    }
}