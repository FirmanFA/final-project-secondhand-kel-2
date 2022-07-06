package com.binar.secondhand.kel2.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.resource.Status
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.binar.secondhand.kel2.databinding.FragmentDetailProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin


class DetailProductFragment :
    BaseFragment<FragmentDetailProductBinding>(FragmentDetailProductBinding::inflate) {
//    private var _binding: FragmentDetailProductBinding? = null
//    private val binding get() = _binding!!
    private val viewModel: DetailProductViewModel by viewModel()
    private val args: DetailProductFragmentArgs by navArgs()
    private var isBid = false


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = args.productId

        setUpObserver()
        val token = getKoin().getProperty("access_token", "")
//
//        KoinJavaComponent.getKoin().setProperty("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTU0NzMyMzJ9.HEJjV4U4jjbzzEM8Di5Nuzj9qQqFXkWn4-aW3l5URa0")
        viewModel.getDetailProduct(productId)
        viewModel.getBuyerOrder()

        binding.btnTertarik.setOnClickListener {
            Snackbar.make(binding.snackbar, "Harga tawaranmu berhasil dikirim ke penjual", Snackbar.LENGTH_LONG)
                .setAction("x") {
                    // Responds to click on the action
                }
                .setBackgroundTint(resources.getColor(R.color.Green))
                .setActionTextColor(resources.getColor(R.color.white))
                .show()
            var modal = BuyerPenawaranFragment(
                productId!!,
                refreshButton = { viewModel.getBuyerOrder() }
            )
            modal.show(parentFragmentManager, "Tag")
//

        }

        viewModel.getBuyerOrder.observe(viewLifecycleOwner) {
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == productId) {
                    isBid = true
                }
            }
            if (isBid) {
                binding.btnTertarik.isEnabled = false
                binding.btnTertarik.text = "Menunggu Respon Penjual"
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.grey)
            }
            else{
                binding.btnTertarik.isEnabled = true
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.primary_blue)
            }
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
        viewModel.getProfile.observe(viewLifecycleOwner) {
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
                        tvLocation.text = it.data?.body()?.address
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