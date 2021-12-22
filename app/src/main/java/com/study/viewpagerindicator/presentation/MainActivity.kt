package com.study.viewpagerindicator.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.study.viewpagerindicator.R
import com.study.viewpagerindicator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val cardFragment1 by lazy { CardFragment() }
    private val cardFragment2 by lazy { CardFragment() }
    private var activeFragment = cardFragment1 as Fragment

    private val tabSelectedListener: TabLayout.OnTabSelectedListener =
        object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> changeFragment(cardFragment1)
                    else -> changeFragment(cardFragment2)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initTabLayout()
    }

    private fun initTabLayout() {
        binding.tabLayout.apply {
            addTab(newTab().setText("TAB1"))
            addTab(newTab().setText("TAB2"))
            addOnTabSelectedListener(tabSelectedListener)
        }
        changeFragment(cardFragment1)
    }

    private fun changeFragment(currentFragment: Fragment) {
        val fm = supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(activeFragment)

        if (!currentFragment.isAdded) {
            fm.add(R.id.frame_layout, currentFragment, currentFragment.toString())
                .show(currentFragment).commit()
        } else if (activeFragment != currentFragment) {
            fm.show(currentFragment).commit()
        }
        activeFragment = currentFragment
    }

}