package com.binar.secondhand.kel2.ui.detail.dialog

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
import com.binar.secondhand.kel2.databinding.FragmentDialogInputBinding
import com.binar.secondhand.kel2.databinding.FragmentLogoutBinding
import com.binar.secondhand.kel2.ui.detail.DetailProductFragmentDirections
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogDeleteFragment(
    productId: Int,
     orderId: Int,
    private val refreshButton: () -> Unit
) : DialogFragment() {
    private var _binding : FragmentDialogInputBinding? = null
    private val binding get() = _binding!!
    private var orderId = orderId
    private var productId = productId
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
            viewModel.deleteOrder(orderId)
            refreshButton.invoke()


        }
    }

    private fun setUpObserver() {

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