package com.binar.secondhand.kel2.ui.detail

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.resource.Status
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.binar.secondhand.kel2.databinding.FragmentDetailProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.java.KoinJavaComponent


class DetailProductFragment :
    BaseFragment<FragmentDetailProductBinding>(FragmentDetailProductBinding::inflate) {
//    private var _binding: FragmentDetailProductBinding? = null
//    private val binding get() = _binding!!
    private val viewModel: DetailProductViewModel by viewModel()
    private val args: DetailProductFragmentArgs by navArgs()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = 2
//        val UserId = 75
//        viewModel.getUserProfile(UserId)
        setUpObserver()

        KoinJavaComponent.getKoin().setProperty("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTU0NzMyMzJ9.HEJjV4U4jjbzzEM8Di5Nuzj9qQqFXkWn4-aW3l5URa0")
        viewModel.getDetailProduct(productId)

        binding.btnTertarik.setOnClickListener {
            Snackbar.make(binding.snackbar, "Harga tawaranmu berhasil dikirim ke penjual", Snackbar.LENGTH_LONG)
                .setAction("x") {
                    // Responds to click on the action
                }

                .setBackgroundTint(resources.getColor(R.color.Green))
                .setActionTextColor(resources.getColor(R.color.white))
                .show()
            findNavController().navigate(R.id.action_detailProductFragment_to_buyerPenawaranFragment)
        }
    }

    @SuppressLint("CheckResult")
    private fun setUpObserver() {
        viewModel.detailProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    //loading state, misal menampilkan progressbar
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {

                    binding.progressBar.visibility = View.GONE
                    //sukses mendapat response, progressbar disembunyikan lagi
                    Glide.with(binding.ivBackdrop)
                        .load(it.data?.body()?.imageUrl)
                        .error(R.drawable.ic_broken)
                        .into(binding.ivBackdrop)

                    binding.apply {
//                        val category = arrayListOf<String>()
//                        it.data?.body()?.categories?.forEach { categories ->
//                            category.add(categories.name)
//                        }
                        tvCategory.text = it.data?.body()?.categories?.joinToString(){
                            it.name
                        }

                        tvTitle.text = it.data?.body()?.name
                        tvPrice.text = it.data?.body()?.basePrice.toString()
                        tvDesc.text = it.data?.body()?.description.toString()

                        tvName.text = it.data?.body()?.user?.fullName.toString()
                        tvLocation.text = it.data?.body()?.user?.address.toString()
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(
                        requireContext(),
                        "Error get Data : ${error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        viewModel.GetProfile.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    //loading state, misal menampilkan progressbar
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {

                    binding.progressBar.visibility = View.GONE
                    //sukses mendapat response, progressbar disembunyikan lagi
                    Glide.with(binding.imgProfile)
                        .load(it.data?.body()?.imageUrl)
                        .error(R.drawable.ic_broken)
                        .into(binding.imgProfile)

                    binding.apply {
                        tvTitle.text = it.data?.body()?.fullName
                        tvLocation.text = it.data?.body()?.city
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(
                        requireContext(),
                        "Error get Data : $error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}