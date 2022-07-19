package com.binar.secondhand.kel2.ui.detail.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.binar.secondhand.kel2.R
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
    private val viewModel: DetailProductViewModel by viewModel()
    lateinit var etMoney: EditText

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

        Glide.with(binding.imgProfile)
            .load(imageProduct)
            .error(R.drawable.ic_broken)
            .into(binding.imgProfile)

        binding.tvName.text = product
        val formatter: NumberFormat = DecimalFormat("#,###")
        val myNumber = price.toInt()
        val formattedNumber: String = formatter.format(myNumber).toString()
        price = "Rp. $formattedNumber"
        price.toString().replace("Rp. ", "").replace(".", "")
        binding.tvPrice.text = price

        binding.btnEdit.setOnClickListener{

        }

        binding.btnDelete.setOnClickListener{
            viewModel.deleteOrder(productId)
        }
    }


}