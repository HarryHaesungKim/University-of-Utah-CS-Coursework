package com.cs4530project.lifestyleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HikesFragment: Fragment(), OnMapReadyCallback, View.OnClickListener {

    private var tvLocation: TextView? = null
    private var mapView: MapView? = null
    private var buttonFindHikes: Button? = null
    private var googleMap: GoogleMap? = null
    private var userLocation: LatLng? = null
    private var hikes: List<MapsData>? = null

    private val mLifestyleViewModel: LifestyleViewModel by activityViewModels()

    private val hikeObserver: Observer<List<MapsData>?> =
        Observer { hikeData ->
            hikes = hikeData
            showHikes()
        }

    private val userObserver: Observer<UserData?> =
        Observer { userData -> // Update the UI if this data variable changes
            if (userData != null) {
                tvLocation!!.text = "${userData.city}, ${userData.country}"
                val loc = LatLng(userData.latitude, userData.longitude)
                if (loc != userLocation) {
                    userLocation = loc
                    hikes = null
                    focusLocation()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hikes, container, false)

        mapView = view.findViewById(R.id.map_view) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)

        tvLocation = view.findViewById(R.id.tv_location_data) as TextView

        buttonFindHikes = view.findViewById(R.id.button_find_hikes) as Button
        buttonFindHikes!!.setOnClickListener(this)

        mLifestyleViewModel.user.observe(viewLifecycleOwner, userObserver)
        mLifestyleViewModel.hikes.observe(viewLifecycleOwner, hikeObserver)

        return view
    }

    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
            focusLocation()
            showHikes()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_find_hikes -> {
                mLifestyleViewModel.getHikes()
            }
        }
    }

    private fun focusLocation() {
        googleMap?.clear()
        if (userLocation != null) {
            googleMap?.addMarker(
                MarkerOptions()
                    .position(userLocation!!)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .title("My Location")
            )
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation!!, 12f))
        }
    }

    private fun showHikes() {
        if (hikes != null) {
            for (hike in hikes!!) {
                googleMap?.addMarker(
                    MarkerOptions()
                        .position(LatLng(hike.lat!!, hike.long!!))
                        .title(hike.name)
                )
            }
        }
        if (userLocation != null) {
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation!!,12f))
        }
    }
}