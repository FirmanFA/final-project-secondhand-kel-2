package com.binar.secondhand.kel2.ui.detail.dialog

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentDialogInputBinding
import com.binar.secondhand.kel2.databinding.FragmentLogoutBinding
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogInputFragment(
     productId: Int,
    price: Int,
    private val refreshButton: () -> Unit
) : DialogFragment() {
    private var _binding : FragmentDialogInputBinding? = null
    private val binding get() = _binding!!
    private var productId = productId

    private var price = price
    private val viewModel: DetailProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogInputBinding.inflate(inflater, container, false)
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
            val buyerPenawaran = PostOrderRequest(
                productId,
                price
            )
            viewModel.buyerOrder(buyerPenawaran)


            refreshButton.invoke()


        }
    }

    private fun setUpObserver() {

        viewModel.buyerOrder.observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    when (it.data?.code()){
                        201 -> {

                            getActivity()?.let { it1 ->
                                Snackbar.make(
                                    it1.findViewById(R.id.snackbar_detail),
                                    "Harga tawaranmu berhasil dikirim ke penjual",
                                    Snackbar.LENGTH_LONG
                                )
                                    .setAction("x") {
                                        // Responds to click on the action
                                    }
                                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.Green))
                                    .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                                    .show()
                            }


                            Toast.makeText(context, "Penawaran Anda Diterima", Toast.LENGTH_SHORT).show()
                            refreshButton.invoke()
                            dismiss()

                        }
                        400 ->{
                            Toast.makeText(context, "Anda Telah Menawar Produk Ini", Toast.LENGTH_SHORT).show()
                            dismiss()
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
                            Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT).show()
                        }
                        503 ->{
                            val error = it.message
                            Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT).show()
                        }

                        else ->{
                            val error = it.message
                            Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}