package com.binar.secondhand.kel2.ui.detail.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.buyer.orderid.get.GetBuyerOrderId
import com.binar.secondhand.kel2.data.api.model.wishlist.get.Product
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentLihatPenawaranBinding
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.NumberFormat

class LihatPenawaranFragment(
    private val productId: Int,
    status: String,
    private val orderId: Int,
    product: String,
    imageProduct: String,
    price: String,
    private val refreshButton: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentLihatPenawaranBinding? = null
    private val binding get() = _binding!!
    private val product = product
    private val imageProduct = imageProduct
    private var price = price
    private val status = status
    private val viewModel: DetailProductViewModel by viewModel()
    lateinit var etMoney: EditText
    private var bid = false
    private var pending = false
    private var accepted = false
    private var decline = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentLihatPenawaranBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var token = getKoin().getProperty("access_token", "")

        binding.btnEdit.setOnClickListener{

        }

        binding.btnDelete.setOnClickListener{
            viewModel.deleteOrder(productId)
        }
        viewModel.getProductOrder(orderId)
        viewModel.getBuyerOrder()
        setUpObserver()
        cekStatus()


    }

    @SuppressLint("SetTextI18n")
    private fun cekStatus(){
        viewModel.getBuyerOrder.observe(viewLifecycleOwner) {
            if (status == "pending"){
                pending = true
            }
            else if (status == "accepted"){
                accepted = true
            }
            else if (status == "decline"){
                decline = true
            }
            else if (status == "bid"){
                bid = true
            }
            if (accepted){
                binding.btnEdit.isEnabled = false
                binding.btnDelete.isEnabled = false
                binding.image.setBackgroundResource(R.drawable.green_gradient)
                binding.imgIcon.setImageResource(R.drawable.ic_succes)
                binding.tvStatus.text = "Penawaran anda Diterima"

            }else if(pending) {
                binding.btnEdit.isEnabled = true
                binding.btnDelete.isEnabled = true
                binding.image.setBackgroundResource(R.drawable.yellow_gradient)
                binding.imgIcon.setImageResource(R.drawable.ic_pending)
                binding.tvStatus.text = "Menunggu respon penjual"

            }else if(bid) {
                binding.btnEdit.isEnabled = true
                binding.btnDelete.isEnabled = true
                binding.image.setBackgroundResource(R.drawable.yellow_gradient)
                binding.imgIcon.setImageResource(R.drawable.ic_pending)
                binding.tvStatus.text = "Menunggu Respon Penjual"
            }

            else if(decline) {
                binding.btnEdit.isEnabled = true
                binding.btnDelete.isEnabled = true
                binding.image.setBackgroundResource(R.drawable.red_gradient)
                binding.imgIcon.setImageResource(R.drawable.ic_failed)
                binding.tvStatus.text = "Penawaran Anda Ditolak"
            }else{
                binding.btnEdit.isEnabled = false
                binding.btnDelete.isEnabled = false
                binding.image.setBackgroundResource(R.color.grey)
                binding.imgIcon.setImageResource(R.drawable.notification_gradient)
                binding.tvStatus.text = "Penawaran tidak diketahui"
            }

        }
    }

    private fun setUpObserver(){
        viewModel.orderProduct.observe(viewLifecycleOwner){it ->
            val price = it.data?.body()?.price.toString()

            when (it.status){
                Status.LOADING ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.image.visibility = View.GONE
                    binding.image.visibility = View.GONE

                }
                Status.SUCCESS ->{
                    binding.progressBar.visibility = View.GONE
                    binding.image.visibility = View.VISIBLE
                    binding.image.visibility = View.VISIBLE

                    when(it.data?.code()){
                        200 -> {
                            Glide.with(binding.imgProfile)
                                .load(imageProduct)
                                .error(R.drawable.ic_broken)
                                .into(binding.imgProfile)
                            binding.tvName.text = product
                            val formatter: NumberFormat = DecimalFormat("#,###")
                            val myNumber = price.toInt()
                            val formattedNumber: String = formatter.format(myNumber).toString()
                            this.price = "Rp. $formattedNumber"
                            price.toString().replace("Rp. ", "").replace(".", "")
                            binding.tvPrice.text = price
                        }
                    }
                }
                Status.ERROR ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.image.visibility = View.GONE
                    binding.image.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(
                        requireContext(),
                        "Error get Data : ${error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }



}