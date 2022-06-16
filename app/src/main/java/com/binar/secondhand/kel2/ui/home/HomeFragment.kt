package com.binar.secondhand.kel2.ui.home

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.FragmentHomeBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setEndIconOnClickListener {
            //do search
        }

        binding.etSearch.editText?.setOnEditorActionListener { textView, i, keyEvent ->

            if (i == EditorInfo.IME_ACTION_SEARCH) {
                //do something with search
            }

            true
        }

        for (i in 0..3) {
            binding.tabHomeCategory.addTab(
                binding.tabHomeCategory.newTab()
                    .setText("tab ke $i")
                    .setIcon(R.drawable.ic_baseline_search_24).setId(i*10)
                    .setTag("ini tag tab ke $i")
            )

        }

        binding.tabHomeCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(context, "${tab?.tag}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }


}