package com.egiwon.architecturestudy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.egiwon.architecturestudy.R
import com.egiwon.architecturestudy.databinding.FgTabBinding
import com.google.android.material.tabs.TabLayoutMediator

class TabFragment : Fragment() {

    private lateinit var binding: FgTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        binding.run {
            vpContent.run {
                adapter = PagerAdapter(this@TabFragment)
            }

            TabLayoutMediator(tlSearch, vpContent) { tab, position ->
                tab.text = getString(Tab.values()[position].stringResId)
            }.attach()
        }
    }
}