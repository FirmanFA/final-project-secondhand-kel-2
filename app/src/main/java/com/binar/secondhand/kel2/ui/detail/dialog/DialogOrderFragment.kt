package com.binar.secondhand.kel2.ui.detail.dialog

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentDialogOrderBinding
import com.binar.secondhand.kel2.databinding.FragmentLogoutBinding
import com.binar.secondhand.kel2.ui.detail.DetailProductFragmentDirections
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogOrderFragment(
    productId: Int,
    orderId: Int,
    harga: Int,
    order: String,
    private val refreshButton: () -> Unit
) : DialogFragment() {
    private var _binding : FragmentDialogOrderBinding? = null
    private val binding get() = _binding!!
    private var orderId = orderId
    private var harga = harga.toString()
    private var productId = productId
    private var order = order
    private val viewModel: DetailProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_radius)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()

        binding.tvKembali.setOnClickListener {
            dialog?.dismiss()
        }

        binding.tvYa.setOnClickListener {
            if (order == "edit"){
                val bid = harga.toRequestBody("text/plain".toMediaTypeOrNull())
                viewModel.editOrder(
                    orderId,
                    bid,
                )
                refreshButton.invoke()
            }
            else if (order == "delete"){
                viewModel.deleteOrder(orderId)
                refreshButton.invoke()
            }
            else{
                refreshButton.invoke()
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpObserver() {
        if (order == "edit"){
            binding.tvTitle.text = "UBAH PENAWARAN"
            binding.tvTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.tvDesc.text = "Apakah anda yakin ingin mengubah penawaran ini? "
            binding.imgIcon.setImageResource(R.drawable.ic_ubah)

        }
        else if (order == "delete"){
            binding.tvTitle.text = "HAPUS PENAWARAN"
            binding.tvTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            binding.tvDesc.text = "Apakah anda yakin ingin menghapus penawaran pada produk ini ?"
            binding.imgIcon.setImageResource(R.drawable.ic_delete)

        }
        viewModel.editOrder.observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING -> {
                    binding.container.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.container.visibility = View.VISIBLE
                    getActivity()?.let { it1 ->
                        Snackbar.make(
                            it1.findViewById(R.id.snackbar_detail),
                            "Penawaranmu pada produk berhasil diubah",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("x") {
                                // Responds to click on the action
                            }
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.Green))
                            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                            .show()
                    }
                    Toast.makeText(context, "Penawaran Anda Telah Diubah", Toast.LENGTH_SHORT).show()

                    refreshButton.invoke()
                    dialog?.dismiss()
                    val action = DetailProductFragmentDirections.actionDetailProductFragmentToSelf(productId)
                    findNavController().navigate(action)


                }
                Status.ERROR -> {
                    binding.container.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.deleteOrder.observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING -> {
                    binding.container.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.container.visibility = View.VISIBLE
                    getActivity()?.let { it1 ->
                        Snackbar.make(
                            it1.findViewById(R.id.snackbar_detail),
                            "Penawaranmu pada produk berhasil dihapus",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("x") {
                                // Responds to click on the action
                            }
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.Green))
                            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                            .show()
                    }
                    Toast.makeText(context, "Penawaran Anda Telah Dihapus", Toast.LENGTH_SHORT).show()

                    refreshButton.invoke()
                    dialog?.dismiss()
                    val action = DetailProductFragmentDirections.actionDetailProductFragmentToSelf(productId)
                    findNavController().navigate(action)


                }
                Status.ERROR -> {
                    binding.container.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}