package com.binar.secondhand.kel2.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.binar.secondhand.kel2.databinding.FragmentExampleBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment

class ExampleFragment :
    BaseFragment<FragmentExampleBinding>(FragmentExampleBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //cal snackbar
        binding.button.setOnClickListener {
            showSnackbar("Testing Snackbar")
        }


        //call snackbar with action
        binding.button2.setOnClickListener {
            showSnackbarWithAction("With Action", "OK") {
                //do something when "OK" Clicked, for example, undo
                Toast.makeText(context, "Action Clicked", Toast.LENGTH_SHORT).show()
            }
        }

    }


}