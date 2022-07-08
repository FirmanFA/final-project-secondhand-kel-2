package com.binar.secondhand.kel2.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.DetailProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.NumberFormat

class DetailProductFragment :
    BaseFragment<DetailProductBinding>(DetailProductBinding::inflate) {
    //    private var _binding: FragmentDetailProductBinding? = null
//    private val binding get() = _binding!!
    private val viewModel: DetailProductViewModel by viewModel()
    private val args: DetailProductFragmentArgs by navArgs()
    private var isBid = false



    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = args.productId
        binding.tvPrice

        setUpObserver()
        getKoin().getProperty("access_token", "")


        viewModel.getDetailProduct(productId)
        viewModel.getBuyerOrder()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnTertarik.setOnClickListener {

            val modal = BuyerPenawaranFragment(
                productId,
                refreshButton = { viewModel.getBuyerOrder() }
            )
            modal.show(parentFragmentManager, "Tag")


        }

        viewModel.getBuyerOrder.observe(viewLifecycleOwner) {
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == productId) {
                    isBid = true
                }
            }
            if (isBid) {
                Snackbar.make(binding.snackbar, "Harga tawaranmu berhasil dikirim ke penjual", Snackbar.LENGTH_LONG)
                    .setAction("x") {
                        // Responds to click on the action
                    }
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.Green))
                    .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()
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


    @SuppressLint("CheckResult", "SetTextI18n")
    private fun setUpObserver() {
        viewModel.detailProduct.observe(viewLifecycleOwner) { it ->
            val price = it.data?.body()?.basePrice.toString()
            when (it.status) {
                Status.LOADING -> {
                    binding.coordinator1.visibility = View.GONE
                    binding.coordinator2.visibility = View.GONE
                    binding.coordinator3.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val formatter: NumberFormat = DecimalFormat("#,###")
                    val myNumber = price.toInt()
                    val formattedNumber: String = formatter.format(myNumber).toString()
                    //formattedNumber is equal to 1,000,000

                    binding.coordinator1.visibility = View.VISIBLE
                    binding.coordinator2.visibility = View.VISIBLE
                    binding.coordinator3.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    //sukses mendapat response, progressbar disembunyikan lagi
                    Glide.with(binding.ivBackdrop)
                        .load(it.data?.body()?.imageUrl)
                        .error(R.drawable.ic_broken)
                        .into(binding.ivBackdrop)

                    binding.apply {

                        tvCategory.text = it.data?.body()?.categories?.joinToString {
                            it.name
                        }
                        tvTitle.text = it.data?.body()?.name
                        binding.tvPrice.text = "Rp. $formattedNumber"
                        val price = tvPrice.text.toString().replace("Rp. ", "").replace(".", "")

                        tvPrice.text = "Rp. $price"



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