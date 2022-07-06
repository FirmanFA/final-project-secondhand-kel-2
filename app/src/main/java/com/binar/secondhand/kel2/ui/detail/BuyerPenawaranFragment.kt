package com.binar.secondhand.kel2.ui.detail


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentBuyerPenawaranBinding
import com.binar.secondhand.kel2.databinding.FragmentDetailProductBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent


class BuyerPenawaranFragment(
    productId: Int,
    private val refreshButton: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBuyerPenawaranBinding? = null
    private val binding get() = _binding!!
    private val productId = productId
    private val viewModel: BuyerPenawaranViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentBuyerPenawaranBinding.inflate(layoutInflater)
        return binding.root
    }


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = getKoin().getProperty("access_token", "")
        viewModel.getDetailProduct(productId)
        setUpObserver()


        binding.btnKirim.setOnClickListener{
            if (binding.etHargaTawar.text.isNullOrEmpty()) {
                binding.textField.error = "Input tawar harga tidak boleh kosong"

            }else {
                val hargaTawar = binding.etHargaTawar.text
                val buyerPenawaran = PostOrderRequest(
                    productId,
                    hargaTawar.toString().toInt()
                )
                viewModel.buyerOrder(buyerPenawaran)
                refreshButton()
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
                            refreshButton.invoke()
                            dismiss()

                        }
                        400 ->{
                            Toast.makeText(context, "Anda Telah Menawar Produk Ini", Toast.LENGTH_SHORT).show()
                            refreshButton.invoke()
                        }
                        403 ->{
                            Toast.makeText(context, "Kamu Belum Login", Toast.LENGTH_SHORT).show()
                            refreshButton.invoke()
                        }
                        else ->{
                            Toast.makeText(context, "Penawaran Anda Bermasalah", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    when (it.data?.code()){
                        500 ->{
                            val error = it.message
                            Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                        }
                        503 ->{
                            val error = it.message
                            Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                        }

                        else ->{
                            val error = it.message
                            Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun dismiss() {
        super.dismiss()

    }


}