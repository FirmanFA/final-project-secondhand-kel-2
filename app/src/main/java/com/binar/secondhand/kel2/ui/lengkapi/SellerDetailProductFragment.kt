package com.binar.secondhand.kel2.ui.lengkapi

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponseItem
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentSellerDetailProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.kel2.ui.main.MainFragmentDirections
import com.binar.secondhand.kel2.utils.URIPathHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.lang.ref.WeakReference
import java.text.DecimalFormat


class SellerDetailProductFragment :
    BaseFragment<FragmentSellerDetailProductBinding>(FragmentSellerDetailProductBinding::inflate) {

    private val sellerDetailProductViewModel: SellerDetailProductViewModel by viewModel()

    private var imageUri: Uri? = null
    private val listCategory = ArrayList<Pair<Boolean,GetCategoryResponseItem>>()
    private val listSelectedCategory = ArrayList<GetCategoryResponseItem>()
    private val listSelectedCategoryId = ArrayList<Int>()

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data
                    imageUri = fileUri
                    loadImage(fileUri)

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etMoney: EditText = binding.etPrice.editText!!

        binding.btnAddCategory.isEnabled = false
        binding.btnAddCategory.setOnClickListener {
            val selectCategoryDialog = CategoryBottomDialog(listCategory){
                listSelectedCategory.addAll(it)

            }
            selectCategoryDialog.show(parentFragmentManager, "select_category")
        }

        //delimiter
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

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        val token = getKoin().getProperty("access_token", "")

        if (token == "") {
            binding.tvProduct.visibility = View.GONE
            binding.tvName.visibility = View.GONE
            binding.etName.visibility = View.GONE
            binding.tvPrice.visibility = View.GONE
            binding.etPrice.visibility = View.GONE
            binding.tvCity.visibility = View.GONE
            binding.etCity.visibility = View.GONE
            binding.tvKategori.visibility = View.GONE
            binding.chipGroupSelectedCategory.visibility = View.GONE
            binding.btnAddCategory.visibility = View.GONE
            binding.tvDescription.visibility = View.GONE
            binding.etDescription.visibility = View.GONE
            binding.tvPhoto.visibility = View.GONE
            binding.ivPhoto.visibility = View.GONE
            binding.btnPreview.visibility = View.GONE
            binding.btnTerbit.visibility = View.GONE

            Log.d("list", "token kosong")

            binding.btnLogin.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }

        } else {
            Log.d("list", "token tidak kosong")
            binding.ivLogin.visibility = View.GONE
            binding.tvLogin.visibility = View.GONE
            binding.btnLogin.visibility = View.GONE
        }

        val city = resources.getStringArray(R.array.city)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, city)
        binding.autoCompleteTv.setAdapter(arrayAdapter)

        MainFragment.activePage = R.id.main_sell

        setUpObservers()
        sellerDetailProductViewModel.getCategory()

        binding.ivPhoto.setOnClickListener {
            openImagePicker()
        }

        binding.btnPreview.setOnClickListener {
            if (binding.etName.editText?.text.toString()
                    .isEmpty() || binding.etPrice.editText?.text.toString()
                    .isEmpty() || binding.etCity.editText?.text.toString()
//                    .isEmpty() || binding.etCategory.editText?.text.toString()
                    .isEmpty() || binding.etDescription.editText?.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    "Lengkapi data terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val actionToPreviewFragment =
                    MainFragmentDirections.actionMainFragmentToPreviewFragment(
                        name = binding.etName.editText?.text.toString(),
                        price = binding.etPrice.editText?.text.toString().replace("Rp. ", "")
                            .replace(",", ""),
                        location = binding.etCity.editText?.text.toString(),
                        description = binding.etDescription.editText?.text.toString(),
                        image = imageUri.toString(),
//                        category = binding.etCategory.editText?.text.toString()
                        category = ""
                    )
                findNavController().navigate(actionToPreviewFragment)
            }
        }

        binding.btnTerbit.setOnClickListener {
            binding.apply {
                val name = etName.editText?.text.toString()
                val price = etPrice.editText?.text.toString().replace("Rp. ", "").replace(",", "")
                val city = etCity.editText?.text.toString()
//                val category = etCategory.editText?.text.toString()
                val category = ""
                val description = etDescription.editText?.text.toString()

                val imageFile = if (imageUri == null) {
                    null
                } else {
                    File(URIPathHelper.getPath(requireContext(), imageUri!!).toString())
                }

                val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
                val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
                val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
                val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())

                val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageBody = requestImage?.let {
                    MultipartBody.Part.createFormData("image", imageFile.name, it)
                }

                sellerDetailProductViewModel.postProduct(
                    name = nameBody,
                    base_price = priceBody,
                    location = cityBody,
                    category_ids = categoryBody,
                    description = descriptionBody,
                    image = imageBody
                )
            }
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun loadImage(uri: Uri?) {
        uri?.let {
            Glide.with(this)
                .load(it)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .into(binding.ivPhoto)
        }
    }

    private fun setUpObservers() {
        sellerDetailProductViewModel.sellerPostProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        201 -> {
                            MainFragment.activePage = R.id.main_sale_list
                            MainFragment.statusTerbit = "sukses"
                            findNavController().navigate(R.id.action_mainFragment_self)
                        }
                        503 -> {
                            Snackbar.make(
                                binding.snackbar,
                                "Server sedang mengalami gangguan, harap coba lagi nanti.",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("x") {
                                    // Responds to click on the action
                                }
                                .setBackgroundTint(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red
                                    )
                                )
                                .setActionTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                .show()
                        }
                        else -> {
                            Snackbar.make(
                                binding.snackbar,
                                "Terjadi kesalahan",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("x") {
                                    // Responds to click on the action
                                }
                                .setBackgroundTint(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red
                                    )
                                )
                                .setActionTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                .show()
                        }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Gagal terbit", Toast.LENGTH_SHORT).show()
                }
            }
        }

        sellerDetailProductViewModel.getCategoryResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }

                Status.SUCCESS -> {

                    when (it.data?.code()) {
                        200 -> {
//                            it.data.body()?.let { it1 -> listCategory.addAll(it1) }
//                            binding.etCategory.isEnabled = true
                            val rawCategory = it.data.body()
                            rawCategory?.forEach { getCategoryResponseItem ->
                                if (listSelectedCategoryId.contains(getCategoryResponseItem.id)){
                                    listCategory.add(Pair(true,getCategoryResponseItem))
                                }else{
                                    listCategory.add(Pair(false,getCategoryResponseItem))
                                }
//                                if (listSelectedCategory.contains(getCategoryResponseItem)){
//                                        listCategory.add(Pair(true,getCategoryResponseItem))
//                                }else{
//                                    listCategory.add(Pair(false,getCategoryResponseItem))
//                                }
                            }
                            binding.btnAddCategory.isEnabled = true
                        }
                    }
                }

                Status.ERROR -> {
                }
            }
        }

    }

    fun EditText.setMaskingMoney(currencyText: String) {
//        set delimiter
        this.addTextChangedListener(object : MyTextWatcher {
            val editTextWeakReference: WeakReference<EditText> =
                WeakReference<EditText>(this@setMaskingMoney)

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

    interface MyTextWatcher : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    fun String.monetize(): String = if (this.isEmpty()) "0"
    else DecimalFormat("#,###").format(this.replace("[^\\d]".toRegex(), "").toLong())
}