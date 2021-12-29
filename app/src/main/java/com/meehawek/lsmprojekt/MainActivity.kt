package com.meehawek.lsmprojekt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayout
import com.meehawek.lsmprojekt.ui.main.adapters.ViewPagerAdapter
import com.meehawek.lsmprojekt.viewmodels.ScoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._com_meehawek_lsmprojekt_ui_main_ScoresFragment_GeneratedInjector


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var mAdView : AdView
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var viewModel: ScoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ads
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        MobileAds.initialize(this) {}

        // tab layout
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tabs)

        viewPagerAdapter = ViewPagerAdapter(
                getSupportFragmentManager())

        viewPager.setAdapter(viewPagerAdapter)

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)?.setIcon(R.drawable.baseline_emoji_events_24)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.baseline_looks_one_24)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.baseline_looks_two_24)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.baseline_favorite_24)
        //var navController = findNavController(R.id.nav_host_fragment)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}