package com.binar.secondhand.kel2.ui.detail.dialog


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentBuyerPenawaranBinding
import com.binar.secondhand.kel2.ui.MainActivity
import com.binar.secondhand.kel2.ui.account.LogoutFragment
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.NumberFormat


class BuyerPenawaranFragment(
    private val productId: Int,
    product: String,
    imageProduct: String,
    price: String,
    private val refreshButton: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBuyerPenawaranBinding? = null
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
        _binding =FragmentBuyerPenawaranBinding.inflate(layoutInflater)
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
            setUpObserver()
        }

        Glide.with(binding.imgProfile)
            .load(imageProduct)
            .centerCrop()
            .transform(CenterCrop(), RoundedCorners(12))
            .error(R.drawable.ic_broken)
            .into(binding.imgProfile)
        binding.tvName.text = product
        val formatter: NumberFormat = DecimalFormat("#,###")
        val myNumber = price.toInt()
        val formattedNumber: String = formatter.format(myNumber).toString()
        price = "Rp. $formattedNumber"
        price.toString().replace("Rp. ", "").replace(".", "")
        binding.tvPrice.text = price



        //delimiter
        etMoney = binding.etHargaTawar
        etMoney.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!s.toString().startsWith("Rp. ")) {
                    etMoney.setMaskingMoney("Rp. ")
                    Selection.setSelection(etMoney.text, etMoney.text!!.length)
                }
            }
        })

        binding.btnKirim.setOnClickListener{
            if (binding.etHargaTawar.text.isNullOrEmpty()) {
                binding.textField.error = "Input tawar harga tidak boleh kosong"

            }else {
                binding.etHargaTawar.text
                val harga = etMoney.text.toString().replace("Rp. ", "").replace(",", "")
                val buyerPenawaran = PostOrderRequest(
                    productId,
                    harga.toInt()
                )

                viewModel.buyerOrder(buyerPenawaran)
                refreshButton()
            }

        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUpObserver() {
        viewModel.buyerOrder.observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.dialogBottom.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.dialogBottom.visibility = View.VISIBLE
                    when (it.data?.code()){
                        200 or 201 -> {

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
                    binding.dialogBottom.visibility = View.VISIBLE
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


    fun EditText.setMaskingMoney(currencyText: String) {
//        set delimiter
        this.addTextChangedListener(object: MyTextWatcher {
            val editTextWeakReference: WeakReference<EditText> = WeakReference<EditText>(this@setMaskingMoney)
            override fun afterTextChanged(editable: Editable?) {
                val editText = editTextWeakReference.get() ?: return
                val s = editable.toString()
                editText.removeTextChangedListener(this)
                val cleanString = s.replace("[Rp,. ]".toRegex(), "")
                val newval = currencyText + cleanString.monetize()

                editText.setText(newval)
                editText.setSelection(newval.length)
                editText.addTextChangedListener(this)
            }
        })
    }

    interface MyTextWatcher: TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    fun String.monetize(): String = if (this.isEmpty()) "0"
    else DecimalFormat("#,###").format(this.replace("[^\\d]".toRegex(),"").toLong())


}