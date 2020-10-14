package com.badcompany.pitak.ui.main

import android.animation.LayoutTransition
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.badcompany.pitak.App
import com.badcompany.pitak.R
//import com.badcompany.pitak.di.viewmodels.MainViewModelFactory
//import com.badcompany.pitak.fragments.MainNavHostFragment
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.addpost.AddPostActivity
import com.badcompany.pitak.ui.auth.AuthActivity
import com.badcompany.pitak.ui.main.mytrips.MyTripsFragment
import com.badcompany.pitak.ui.main.profile.ProfileFragment
import com.badcompany.pitak.ui.main.searchtrip.SearchTripFragment
import com.badcompany.pitak.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject
import javax.inject.Named


class MainActivity : BaseActivity()/*, BottomNavControllerFix.OnNavigationGraphChanged,
    BottomNavControllerFix.OnNavigationReselectedListener*/ {

//    @Inject
//    lateinit var viewModelFactory: MainViewModelFactory
//
//    @Inject
//    @Named("MainFragmentFactory")
//    lateinit var fragmentFactory: FragmentFactory

//    override fun inject() {
//        (application as App).mainComponent()
//            .inject(this)
//    }

//    private val viewModel: MainViewModel by viewModels {
//        viewModelFactory
//    }

//    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
//        BottomNavControllerFix(
//            this,
//            R.id.main_fragments_container,
//            R.id.nav_menu_search,
//            this)
//    }

    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        checkUserLogin()
//        inject()
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        app_bar.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        setupActionBar()
        setupListeners()
        subscribeObservers()
//        onRestoreInstanceState()
//        setupBottomNavigationView(savedInstanceState)

//        val mConfig = SlidrConfig.Builder()
//            .primaryColor(primary)
//            .position(SlidrPosition.HORIZONTAL)
//            .velocityThreshold(2400f) //                .distanceThreshold(.25f)
//            //                .edge(true)
//            .touchSize(SizeUtils.dpToPx(this, 32))
//            .sensitivity(1f)
//            .scrimColor(Color.BLACK)
//            .scrimStartAlpha(0.8f)
//            .scrimEndAlpha(0f)
//            .velocityThreshold(2400F)
//            .distanceThreshold(0.25f)
//            .edge(true).edgeSize(0.18f)
//            .build()
//
//        // Attach the Slidr Mechanism to this activity
//        val slidr = Slidr.attach(this, mConfig)
//        slidr.unlock()

        nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
    }

    private fun setupListeners() {
        addPost.setOnClickListener {
            start<AddPostActivity>()
        }
    }

    @ExperimentalSplittiesApi
    private fun checkUserLogin() {
        if (AppPreferences.token.isBlank()) {
            start<AuthActivity> { }
        }
    }

//    private fun setupBottomNavigationView(savedInstanceState: Bundle?) {
//        nav_view.setUpNavigation(bottomNavController, this)
//        if (savedInstanceState == null) {
//            bottomNavController.setupBottomNavigationBackStack(null)
//            bottomNavController.onNavigationItemSelected()
//        } else {
//            (savedInstanceState[BOTTOM_NAV_BACKSTACK_KEY] as IntArray?)?.let { items ->
//                val backstack = BottomNavControllerFix.BackStack()
//                backstack.addAll(items.toTypedArray())
//                bottomNavController.setupBottomNavigationBackStack(backstack)
//            }
//        }
//    }


    private fun subscribeObservers() {
    }

//    override fun onReselectNavItem(
//        navController: NavController,
//        fragment: Fragment
//    ) {
//        Log.d(TAG, "logInfo: onReSelectItem")
//        when (fragment) {
//            is SearchTripFragment -> {
////                navController.navigate(R.id.action_navSearchTripFragment_self)
//            }
//
//            is MyTripsFragment -> {
////                navController.navigate(R.id.action_nav_menu_my_trips_self)
//            }
//
//            is ProfileFragment -> {
////                navController.navigate(R.id.action_nav_menu_profile_self)
//            }
//            else -> {
//                // do nothing
//            }
//        }
//    }

//    private fun onRestoreInstanceState() {
//        val host = supportFragmentManager.findFragmentById(R.id.main_fragments_container)
//        host?.let { } ?: createNavHost()
//    }

//    private fun createNavHost() {
//        val navHost = MainNavHostFragment.create(R.navigation.main_nav_graph)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_fragments_container, navHost, getString(
//                R.string.MainNavHost))
//            .setPrimaryNavigationFragment(navHost)
//            .commit()
//    }

//    override fun onGraphChange() {
//
//    }

//    override fun onBackPressed() = bottomNavController.onBackPressed()

    private fun setupActionBar() {
//        setSupportActionBar(tool_bar)
    }

    fun showTabLayout() {
        tab_layout.visibility = View.VISIBLE
    }

    fun hideTabLayout() {
        tab_layout.visibility = View.GONE
    }


}

