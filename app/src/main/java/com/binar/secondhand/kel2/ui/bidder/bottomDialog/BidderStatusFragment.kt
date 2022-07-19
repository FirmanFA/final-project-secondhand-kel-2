package com.binar.secondhand.kel2.ui.bidder.bottomDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdRequest
import com.binar.secondhand.kel2.data.api.model.seller.product.id.patch.PatchProductIdRequest
import com.binar.secondhand.kel2.databinding.FragmentBidderStatusBinding
import com.binar.secondhand.kel2.ui.bidder.BidderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel


class BidderStatusFragment (
    orderId: Int,
    productId: Int?
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBidderStatusBinding? = null
    private val binding get() = _binding!!
    private val orderId = orderId
    private val productId = productId
    private val viewModel: BidderViewModel by viewModel()
    private val status = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidderStatusBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var token = getKoin().getProperty("access_token", "")

        if (token == "") {

            binding.dialogLogin.visibility = View.VISIBLE
            binding.dialogBottom.visibility = View.GONE
            binding.btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_detailProductFragment_to_loginFragment)
                dismiss()
            }

        } else {
            binding.dialogLogin.visibility = View.GONE
            binding.dialogBottom.visibility = View.VISIBLE
        }

        binding.btnKirim.setOnClickListener {
            if (binding.radioGroup.checkedRadioButtonId != -1) {
                if(binding.rbBerhasil.isChecked){
                    if (productId != null) {
                        viewModel.statusProduct(productId, PatchProductIdRequest(status = "seller"))
                    }
                    Toast.makeText(context, "Produk anda berhasil terjual", Toast.LENGTH_SHORT).show()
                    dismiss()
                } else if(binding.rbBatalkan.isChecked){
                    viewModel.statusItem(orderId, PatchSellerOrderIdRequest(status = "declined"))
                    Toast.makeText(context, "Penawaran pada produk anda dibatalkan", Toast.LENGTH_SHORT).show()
                    dismiss()
                }else{
                    Toast.makeText(context, "Pilih status terlebih dahulu", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }


}