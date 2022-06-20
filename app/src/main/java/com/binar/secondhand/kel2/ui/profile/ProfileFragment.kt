package com.binar.secondhand.kel2.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentProfileBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.getKoin

class ProfileFragment :
    BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    //create instance viewModel
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call this method first
        setUpObserver()

        //set access token setelah dapet dari login
        getKoin().setProperty("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTU0NzMyMzJ9.HEJjV4U4jjbzzEM8Di5Nuzj9qQqFXkWn4-aW3l5URa0")

        profileViewModel.getAuth()

//        binding.btnSave.setOnClickListener {
//            val authPutRequest = PutAuthRequest(binding.etName.editText?.text.toString(),)
//            binding.etName.editText?.setText(it.data.body()?.fullName)
//            binding.etAddress.editText?.setText(it.data.body()?.address)
//            binding.etPhone.editText?.setText(it.data.body()?.phoneNumber.toString())
//        }

    }

    private fun setUpObserver(){

        //only call all the observer here once, do not call the observer somewhere else

        profileViewModel.authPutResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {
                    //loading state, misal menampilkan progressbar
                    binding.pbLoading.visibility = View.VISIBLE
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
                            //val result = data
                        }
                        //jika code response 403
                        403 ->{
                            //for example if 403 is wrong response "not authorized"
                            //do something for this code, shoing snackbar for example
                            Toast.makeText(requireContext(), "Error code : 403", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                Status.ERROR ->{
                    //error state
                    //error cause, for example, you can toast it to show error
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        profileViewModel.authGetResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {
                    //loading state, misal menampilkan progressbar
                    binding.pbLoading.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {

                    binding.pbLoading.visibility = View.GONE

                    when(it.data?.code()){
                        200 ->{
                            binding.etName.editText?.setText(it.data.body()?.fullName)
                            binding.etCity.editText?.setText(it.data.body()?.city)
                            binding.etAddress.editText?.setText(it.data.body()?.address)
                            binding.etPhone.editText?.setText(it.data.body()?.phoneNumber.toString())
                        }
                    }
                }

                Status.ERROR ->{
                    binding.pbLoading.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}