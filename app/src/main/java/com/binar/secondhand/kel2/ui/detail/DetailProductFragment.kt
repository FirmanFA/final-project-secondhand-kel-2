package com.binar.secondhand.kel2.ui.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.wishlist.post.PostWishlistRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.DetailProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.detail.dialog.BuyerPenawaranFragment
import com.binar.secondhand.kel2.ui.detail.dialog.LihatPenawaranFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.NumberFormat

class DetailProductFragment() :
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
    private var basePrice = ""
    private var wishlist = true
    private var wishlistId = 0



    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = args.productId
        binding.tvPrice
        setUpObserver()
        observerWishlist()
        getKoin().getProperty("access_token", "")

        viewModel.getDetailProduct(productId)
        viewModel.getBuyerOrder()
        viewModel.getWishlist()


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
                        .centerCrop()
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
                    basePrice = it.data.get(data).basePrice.toString()
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
                        basePrice,
                        refreshButton = { viewModel.getBuyerOrder() }
                    )
                    modal.show(parentFragmentManager, "Tag")
                }
            }

            if (accepted){
                binding.btnTertarik.text = "Penawaran anda telah berhasil"
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.Green)

            }else if(pending) {
                binding.btnTertarik.text = "Menunggu respon penjual"
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.orange)


            }else if(bid) {
                binding.btnTertarik.text = "Naikan tawaranmu pada Produk"
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.orange)
            }

            else if(declined) {
                binding.btnTertarik.text = "Penawaran anda ditolak"
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.red)
            }else{
                binding.btnTertarik.isEnabled = true
                binding.btnTertarik.backgroundTintList =
                    requireContext().getColorStateList(R.color.primary_blue)
            }

        }

    }
    private fun observerWishlist() {
        val id = args.productId
        viewModel.getWishlist.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    if (it.data.isNullOrEmpty()) {

                    } else {
                        for (idWhishlist in it.data ){
                            if (idWhishlist.productId == args.productId){
                                wishlistId = idWhishlist.id
                            }
                        }

                        for (data in 0 until (it.data.size ?: 0)) {
                            if (it.data.get(data).productId == args.productId) {
                                wishlist = true
                            }
                        }
                        if (wishlist) {
                            binding.ivfav.setImageResource(R.drawable.ic_fav_full)
                        } else {
                            binding.ivfav.setImageResource(R.drawable.ic_fav)
                        }
                        binding.ivfav.setOnClickListener {
                            val request = PostWishlistRequest(id)
                            if (wishlist) {
                                viewModel.deleteWishlist(wishlistId)
                                wishlist = false
                            } else {
                                viewModel.postWishlist(request)
                                wishlist =true
                            }
                        }


                    }

                }
                Status.ERROR -> {
                    AlertDialog.Builder(requireContext()).setMessage(it.message).show()
                }
            }
        }

        viewModel.postWishlist.observe(viewLifecycleOwner) {
            when (it.status){
                Status.LOADING ->{
                }
                Status.SUCCESS ->{
                    viewModel.getWishlist()
                    binding.ivfav.setImageResource(R.drawable.ic_fav_full)
                    Toast.makeText(requireContext(), "add to wishlist", Toast.LENGTH_SHORT).show()

                }
                Status.ERROR ->{

                }
            }
        }
        viewModel.deleteWishlist.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING ->{}
                Status.SUCCESS ->{
                    viewModel.getWishlist()
                    binding.ivfav.setImageResource(R.drawable.ic_fav)
                    Toast.makeText(requireContext(), "delete from wishlist", Toast.LENGTH_SHORT)
                        .show()
                }
                Status.ERROR ->{}
            }
        }
    }


}
