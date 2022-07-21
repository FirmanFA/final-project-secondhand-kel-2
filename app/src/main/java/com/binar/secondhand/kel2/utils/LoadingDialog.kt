package com.binar.secondhand.kel2.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.databinding.DialogLoadingBinding

class LoadingDialog(context: Context): Dialog(context) {

    lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //setting layout
        window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        //making background transparent
        window?.setBackgroundDrawableResource(R.color.transparent)

    }

}