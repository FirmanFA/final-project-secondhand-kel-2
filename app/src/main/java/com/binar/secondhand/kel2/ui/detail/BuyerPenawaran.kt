package com.binar.secondhand.kel2.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentBuyerPenawaranBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent


class BuyerPenawaran :BottomSheetDialogFragment() {
    private var _binding: FragmentBuyerPenawaranBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BuyerPenawaranViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentBuyerPenawaranBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = 2

        KoinJavaComponent.getKoin().setProperty("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTU0NzMyMzJ9.HEJjV4U4jjbzzEM8Di5Nuzj9qQqFXkWn4-aW3l5URa0")
        viewModel.getDetailProduct(productId)
        setUpObserver()


        binding.btnKirim.setOnClickListener{
            val buyerPenawaran = PostOrderRequest(
                productId,
                binding.etHargaTawar.text.toString()

            )
            if (binding.etHargaTawar.text.isNullOrEmpty()) {

                Toast.makeText(context, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.postBuyerOrder(buyerPenawaran)
            }
        }

    }

    private fun setUpObserver() {
        viewModel.detailProduct.observe(viewLifecycleOwner){
            when(it.status){
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
                        val category = arrayListOf<String>()
                        it.data?.body()?.categories?.forEach { categories ->
                            category.add(categories.name)
                        }
                        tvName.text = it.data?.body()?.name
                        tvPrice.text = it.data?.body()?.basePrice.toString()
                    }
                }
                Status.ERROR ->{
                    binding.progressBar.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.buyerOrder.observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    when (it.data?.code()){
                        201 -> {
                            val id = it.data?.body()?.id
                            val buyerId = it.data?.body()?.buyerId
                            val productId = it.data?.body()?.productId
                            val status = it.data?.body()?.status
                            val createdAt = it.data?.body()?.createdAt
                            val updatedAt = it.data?.body()?.updatedAt

                            Toast.makeText(context, "Penawaran Anda Diterima", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                    val error = it.message
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}