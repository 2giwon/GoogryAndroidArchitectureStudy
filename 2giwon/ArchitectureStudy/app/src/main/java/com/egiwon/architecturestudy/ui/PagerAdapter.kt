package com.egiwon.architecturestudy.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.egiwon.architecturestudy.ui.tabs.ContentFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = Tab.values().size

    override fun createFragment(position: Int): Fragment =
        ContentFragment.newInstance(Tab.values()[position])

}