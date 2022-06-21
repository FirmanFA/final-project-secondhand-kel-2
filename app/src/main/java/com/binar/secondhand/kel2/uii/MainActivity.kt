package com.binar.secondhand.kel2.uii

import android.os.Bundle
import com.binar.secondhand.kel2.databinding.ActivityMainBinding
import com.binar.secondhand.kel2.uii.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}