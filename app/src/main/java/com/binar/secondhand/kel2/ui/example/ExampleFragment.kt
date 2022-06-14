package com.binar.secondhand.kel2.ui.example

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.binar.secondhand.kel2.data.api.model.ExamplePostRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentExampleBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExampleFragment :
    BaseFragment<FragmentExampleBinding>(FragmentExampleBinding::inflate) {

    //create instance viewModel
    private val exampleViewModel: ExampleViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call this method first
        setUpObserver()

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

        //example postrequest
        //you can call this method every where, and you can call this multiple times, in different method
        val examplePostRequest = ExamplePostRequest(email = "example")
        exampleViewModel.postExample(examplePostRequest)

        //example get
        exampleViewModel.getExample()

    }

    private fun setUpObserver(){

        //only call all the observer here once, do not call the observer somewhere else

        exampleViewModel.examplePostResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {
                    //loading state, misal menampilkan progressbar
                }

                Status.SUCCESS -> {
                    //sukses mendapat response, progressbar disembunyikan lagi
                    when(it.data?.code()){
                        //jika code response 200
                        200 ->{
                            //if 200 is the response code that contains data
                            //do something with data here, for example, add to recyclerlist

                            val data = it.data.body() //this is the data

                            //we can get the data like this
                            val result = data?.result
                        }
                        //jika code response 403
                        403 ->{
                            //for example if 403 is wrong response "not authorized"
                            //do something for this code, shoing snackbar for example
                        }

                    }
                }

                Status.ERROR ->{
                    //error state
                    //error cause, for example, you can toast it to show error
                    val error = it.message
                }
            }
        }

        exampleViewModel.exampleGetResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {}

                Status.SUCCESS -> {

                    when(it.data?.code()){
                        200 ->{}
                    }
                }

                Status.ERROR ->{}
            }
        }
    }


}