package com.binar.secondhand.kel2.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.DetailProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.detail.dialog.BuyerPenawaranFragment
import com.binar.secondhand.kel2.ui.detail.dialog.LihatPenawaranFragment
import com.bumptech.glide.Glide
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
    private var bid = false
    private var pending = false
    private var accepted = false
    private var declined = false
    private var favourite = false
    private var price = ""
    private var imageProduct = ""
    private var product = ""
    private var orderId = 0
    private var status = ""



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

        binding.ivfav.setOnClickListener{
//            viewModel.getIdWishlist(productId)
        }


        viewModel.getIdWishlist.observe(viewLifecycleOwner){
            if(it.data?.body()?.product_id == productId){
                favourite = true
                binding.ivfav.setImageResource(R.drawable.ic_fav_full)
//                viewModel.deleteWishlist(productId)
            }
            else{
                favourite = false
                binding.ivfav.setImageResource(R.drawable.ic_fav)
//                val addFav = PostWishlistRequest(productId)
//                viewModel.postWishlist(addFav)
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


                        product = it.data?.body()?.name.toString()
                        imageProduct = it.data?.body()?.imageUrl.toString()
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

        viewModel.getBuyerOrder.observe(viewLifecycleOwner) {it ->
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == args.productId) {
                    orderId = it.data.get(data).id
                    product = it.data.get(data).productName.toString()
                    imageProduct = it.data.get(data).product.imageUrl
                    price = it.data.get(data).price.toString()
                    status = it.data.get(data).status

                }


            }
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == args.productId && it.data.get(data).status == "pending") {
                    pending = true

                }
            }
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == args.productId && it.data.get(data).status == "bid") {
                    bid = true

                }
            }
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == args.productId && it.data.get(data).status == "accepted") {
                    accepted = true

                }
            }
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == args.productId && it.data.get(data).status == "declined") {
                    declined = true

                }
            }
            if (accepted||pending||bid||declined) {
                binding.btnTertarik.setOnClickListener {
                    val modal = LihatPenawaranFragment(
                        args.productId,
                        status,
                        orderId,
                        product,
                        imageProduct,
                        price,
                        refreshButton = { viewModel.getBuyerOrder() }
                    )
                    modal.show(parentFragmentManager, "Tag")
                }
            }
            else{
                binding.btnTertarik.setOnClickListener {

                    val modal = BuyerPenawaranFragment(
                        args.productId,
                        product,
                        imageProduct,
                        price,
                        refreshButton = { viewModel.getBuyerOrder() }
                    )
                    modal.show(parentFragmentManager, "Tag")


                }
            }

            if (accepted){
                binding.btnEditTertarik.visibility = View.GONE
                binding.btnTertarik.text = "Penawaran anda telah berhasil"
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.Green)

            }else if(pending) {
                binding.btnEditTertarik.visibility = View.GONE
                binding.btnTertarik.text = "Menunggu respon penjual"
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.orange)


            }else if(bid) {
                binding.btnEditTertarik.visibility = View.GONE
                binding.btnTertarik.text = "Naikan tawaranmu pada Produk"
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.orange)
            }

            else if(declined) {
                binding.btnEditTertarik.visibility = View.GONE
                binding.btnTertarik.text = "Penawaran anda ditolak"
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.red)
            }else{
                binding.btnTertarik.isEnabled = true
                binding.btnEditTertarik.visibility = View.GONE
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.primary_blue)
            }

        }
    }


}