package com.binar.secondhand.kel2.ui.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.ui.notification.NotificationViewModel

class SplashFragment : Fragment() {

    private val sharedPref = "splashScreen"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            val splashScreen: SharedPreferences = requireContext().getSharedPreferences(sharedPref, Context.MODE_PRIVATE)
            val isFirstTime: Boolean = splashScreen.getBoolean("isFirstTime", true)

            if (isFirstTime){
                val editor : SharedPreferences.Editor = splashScreen.edit()
                editor.putBoolean("isFirstTime", false)
                editor.apply()
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
            }
        }, 2000)

    }
}