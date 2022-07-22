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
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentLihatPenawaranBinding
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.NumberFormat

class LihatPenawaranFragment(
    private val productId: Int,
    status: String,
    private val orderId: Int,
    product: String,
    imageProduct: String,
    price: String,
    basePrice: String,
    private val refreshButton: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentLihatPenawaranBinding? = null
    private val binding get() = _binding!!
    private val product = product
    private val imageProduct = imageProduct
    private val status = status
    private val viewModel: DetailProductViewModel by viewModel()
    private var bid = false
    private var pending = false
    private var accepted = false
    private var declined = false
    private var harga = 0
    private var order = ""
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

        binding.btnEdit.setOnClickListener{
            if (binding.etHargaTawar.text.isNullOrEmpty()) {
                binding.textField.error = "Input tawar harga tidak boleh kosong"

            }else {
                binding.etHargaTawar.text
                order = "edit"
                val harga = etMoney.text.toString().replace("Rp. ", "").replace(",", "")
                val modal = DialogOrderFragment(
                    productId,
                    orderId,
                    harga.toInt(),
                    order,
                    refreshButton = { viewModel.getBuyerOrder() }
                )
                modal.show(parentFragmentManager, "Tag")
                dismissNow()
            }
        }

        binding.btnDelete.setOnClickListener{
            order = "delete"
            val modal = DialogOrderFragment(
                productId,
                orderId,
                harga.toInt(),
                order,
                refreshButton = { viewModel.getBuyerOrder() }
            )
            modal.show(parentFragmentManager, "Tag")
            dismissNow()

        }
        viewModel.getProductOrder(orderId)
        viewModel.getBuyerOrder()
        setUpObserver()
        cekStatus()

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
            else if (status == "declined"){
                declined = true
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
                binding.btnEdit.backgroundTintList =
                    requireContext().getColorStateList(R.color.grey)
                binding.btnDelete.backgroundTintList =
                    requireContext().getColorStateList(R.color.grey)

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

            else if(declined) {
                binding.btnEdit.isEnabled = false
                binding.btnDelete.isEnabled = true
                binding.image.setBackgroundResource(R.drawable.red_gradient)
                binding.imgIcon.setImageResource(R.drawable.ic_failed)
                binding.tvStatus.text = "Penawaran Anda Ditolak"
                binding.btnEdit.backgroundTintList =
                    requireContext().getColorStateList(R.color.grey)
            }else{
                binding.btnEdit.isEnabled = false
                binding.btnDelete.isEnabled = false
                binding.image.setBackgroundResource(R.color.grey)
                binding.tvStatus.text = "Penawaran tidak diketahui"
            }

        }
    }

    private fun setUpObserver(){
        viewModel.orderProduct.observe(viewLifecycleOwner){it ->
            var basePrice = it.data?.body()?.base_price.toString()
            var price = it.data?.body()?.price.toString()

            when (it.status){
                Status.LOADING ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.image.visibility = View.GONE
                    binding.content.visibility = View.GONE

                }
                Status.SUCCESS ->{
                    binding.progressBar.visibility = View.GONE
                    binding.image.visibility = View.VISIBLE
                    binding.content.visibility = View.VISIBLE

                    Glide.with(binding.imgProfile)
                        .load(imageProduct)
                        .centerCrop()
                        .transform(CenterCrop(), RoundedCorners(12))
                        .error(R.drawable.ic_broken)
                        .into(binding.imgProfile)

                    binding.tvName.text = product
                    val formatter: NumberFormat = DecimalFormat("#,###")
                    val myNumber = basePrice.toInt()
                    val formattedNumber: String = formatter.format(myNumber).toString()
                    basePrice = "Rp. $formattedNumber"
                    basePrice.toString().replace("Rp. ", "").replace(".", "")
                    binding.tvPrice.text = basePrice

                    binding.textField.editText?.setText(price)

                }
                Status.ERROR ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.image.visibility = View.GONE
                    binding.content.visibility = View.GONE
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

    fun EditText.setMaskingMoney(currencyText: String) {
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