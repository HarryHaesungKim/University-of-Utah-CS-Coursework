package com.cs4530project.lifestyleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), SubmitFragment.RequestSubmissionInterface,
    ProfileFragment.RequestUpdateInterface, View.OnClickListener {

    private val mLifestyleViewModel: LifestyleViewModel by viewModels {
        LifestyleViewModelFactory((application as LifestyleApplication).repository)
    }

    private val caloriesObserver: Observer<CalorieData> =
        Observer { calorieData ->
            calIntakeButton!!.text = String.format("Caloric Intake: %,.0f cals", calorieData.dtci)
        }

    private var firstTime = true;

    private val flowObserverInit: Observer<UserData?> = Observer { userData ->
        if (userData != null) {
            if (userData.name == "") {
                hideButtonsForFirstSubmission()
                if (firstTime) {
                    replaceSubmitFragment()
                    firstTime = false
                }
            } else {
                mLifestyleViewModel.refreshData()
            }
        }
    }

    private val flowObserver: Observer<UserData?> = Observer { userData ->
        if (userData != null) {
            if (userData.name == "") {
                hideButtonsForFirstSubmission()
            } else {
                mLifestyleViewModel.refreshData()
            }
        }
    }

    private fun hideButtonsForFirstSubmission() {
        calIntakeButton!!.visibility = View.INVISIBLE
        navBar!!.visibility = View.INVISIBLE
        drawerNavView!!.visibility = View.INVISIBLE
        drawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private var currentFragment: Fragment? = null
    private var submitFragment: Fragment? = null
    private var profileFragment: Fragment? = null
    private var bmrFragment: Fragment? = null
    private var weatherFragment: Fragment? = null
    private var hikesFragment: Fragment? = null

    // Navigation bar
    private var navBar: BottomNavigationView? = null

    // Caloric Intake view/button
    private var calIntakeButton: Button? = null

    // Navigation drawer for tablets (replaces nav bar)
    private var drawerNavView: NavigationView? = null
    private var toggleDrawer: ActionBarDrawerToggle? = null
    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // navigation bar (phones)
        navBar = findViewById(R.id.navigation_bar)
        navBar!!.setOnItemSelectedListener(navListener)

        // navigation drawer (tablets)
        drawerNavView = findViewById(R.id.drawerNavView)
        drawerNavView!!.setNavigationItemSelectedListener(navListener)
        drawerLayout = findViewById(R.id.drawerLayout)
        toggleDrawer = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout!!.addDrawerListener(toggleDrawer!!)
        toggleDrawer!!.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // caloric intake button
        calIntakeButton = findViewById(R.id.caloric_intake_view_button)
        calIntakeButton!!.setOnClickListener(this)

        // set the observers
        mLifestyleViewModel.calories.observe(this, caloriesObserver)

        // find fragments if they exist
        if (savedInstanceState != null) {
            submitFragment = supportFragmentManager.findFragmentByTag("submit_frag")
            profileFragment = supportFragmentManager.findFragmentByTag("profile_frag")
            bmrFragment = supportFragmentManager.findFragmentByTag("bmr_frag")
            weatherFragment = supportFragmentManager.findFragmentByTag("weather_frag")
            hikesFragment = supportFragmentManager.findFragmentByTag("hikes_frag")

            val currentTag = savedInstanceState.getString("CURRENT_FRAG")
            currentFragment = supportFragmentManager.findFragmentByTag(currentTag)
            if (currentTag == "submit_frag") {
                calIntakeButton!!.visibility = View.INVISIBLE
            }

            mLifestyleViewModel.user.observe(this, flowObserver)

        // one-time creation of fragments
        } else {
            submitFragment = SubmitFragment()
            profileFragment = ProfileFragment()
            bmrFragment = BMRFragment()
            weatherFragment = WeatherFragment()
            hikesFragment = HikesFragment()
            currentFragment = submitFragment

            addFragment(submitFragment as SubmitFragment, "submit_frag")
            addFragment(profileFragment as ProfileFragment, "profile_frag")
            addFragment(bmrFragment as BMRFragment, "bmr_frag")
            addFragment(weatherFragment as WeatherFragment, "weather_frag")
            addFragment(hikesFragment as HikesFragment, "hikes_frag")

            switchFragment(profileFragment, profileFragment)

            mLifestyleViewModel.user.observe(this, flowObserverInit)
        }
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_frag_ph_1, fragment, tag)
            .hide(fragment)
            .commit()
    }

    private fun replaceSubmitFragment() {
        supportFragmentManager.beginTransaction()
            .remove(submitFragment!!)
            .commit()
        submitFragment = SubmitFragment()
        addFragment(submitFragment as SubmitFragment, "submit_frag")
        switchFragment(submitFragment, currentFragment)
    }

    private fun switchFragment(fragmentToShow: Fragment?, fragmentToHide: Fragment?) {
        supportFragmentManager.beginTransaction()
            .hide(fragmentToHide!!)
            .show(fragmentToShow!!)
            .commit()
        currentFragment = fragmentToShow
    }

    private val navListener: (MenuItem) -> Boolean = { item: MenuItem ->
        (when (item.itemId) {
            R.id.nav_profile -> {
                switchFragment(profileFragment, currentFragment)
                calIntakeButton!!.visibility = View.VISIBLE
                true
            }
            R.id.nav_bmr -> {
                switchFragment(bmrFragment, currentFragment)
                calIntakeButton!!.visibility = View.VISIBLE
                true
            }
            R.id.nav_hikes -> {
                switchFragment(hikesFragment, currentFragment)
                calIntakeButton!!.visibility = View.VISIBLE
                true
            }
            R.id.nav_weather -> {
                switchFragment(weatherFragment, currentFragment)
                calIntakeButton!!.visibility = View.VISIBLE
                true
            }
            else -> {
                false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggleDrawer!!.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun requestToUpdate() {
        navBar!!.menu.findItem(R.id.nav_profile).isChecked = true
        calIntakeButton!!.visibility = View.INVISIBLE
        replaceSubmitFragment()
    }

    override fun requestToSubmit() {
        switchFragment(profileFragment, submitFragment)
        navBar!!.visibility = View.VISIBLE
        calIntakeButton!!.visibility = View.VISIBLE
        drawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.caloric_intake_view_button -> {
                this.requestToUpdate()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("CURRENT_FRAG", currentFragment!!.tag)
    }
}
