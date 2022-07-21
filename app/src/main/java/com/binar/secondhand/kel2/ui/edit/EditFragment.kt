package com.binar.secondhand.kel2.ui.edit

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.Editable
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponseItem
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentEditBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.detail.BuyerPenawaranFragment
import com.binar.secondhand.kel2.ui.lengkapi.CategoryBottomDialog
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.kel2.utils.URIPathHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.NumberFormat

class EditFragment : BaseFragment<FragmentEditBinding>(FragmentEditBinding::inflate) {

    private val args: EditFragmentArgs by navArgs()
    private val editViewModel: EditViewModel by viewModel()
    private var imageUri: Uri? = null
    private val listCategory = ArrayList<Pair<Boolean, GetCategoryResponseItem>>()
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

    private fun ChipGroup.addChip(category: GetCategoryResponseItem) {

        Chip(context).apply {
            id = category.id
            text = category.name
            isClickable = true
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                listSelectedCategory.remove(category)
                listSelectedCategoryId.remove(category.id)
                removeView(it)
            }
            addView(this)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.id

        val etMoney: EditText = binding.etPrice.editText!!

        binding.btnAddCategory.isEnabled = false
        binding.btnAddCategory.setOnClickListener {
            val newListCategory = ArrayList<Pair<Boolean, GetCategoryResponseItem>>()
            listCategory.forEach { pair ->
                if (listSelectedCategoryId.contains(pair.second.id)) {
                    newListCategory.add(Pair(true, pair.second))
                    Log.d("selected", pair.second.name)
                } else {
                    newListCategory.add(Pair(false, pair.second))
                }
            }

            val selectCategoryDialog = CategoryBottomDialog(newListCategory) {

                binding.chipGroupSelectedCategory.removeAllViews()

                listSelectedCategory.clear()
                listSelectedCategoryId.clear()
                listSelectedCategory.addAll(it)

                listSelectedCategory.forEach { category ->
                    listSelectedCategoryId.add(category.id)
                    binding.chipGroupSelectedCategory.addChip(category)
                }
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

        val city = resources.getStringArray(R.array.city)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, city)
        binding.autoCompleteTv.setAdapter(arrayAdapter)

        setUpObservers()
        editViewModel.getCategory()
        editViewModel.getDetailProduct(id)

        binding.ivPhoto.setOnClickListener {
            openImagePicker()
        }

        binding.btnTerbit.setOnClickListener {
            binding.apply {
                val name = etName.editText?.text.toString()
                val price = etPrice.editText?.text.toString().replace("Rp. ", "").replace(",", "")
                val city = etCity.editText?.text.toString()
                val listCategoryRequest = listSelectedCategory.joinToString {
                    it.id.toString()
                }.toRequestBody("text/plain".toMediaTypeOrNull())
                val description = etDescription.editText?.text.toString()

                val imageFile = if (imageUri == null) {
                    null
                } else {
                    File(URIPathHelper.getPath(requireContext(), imageUri!!).toString())
                }

                val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
                val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
                val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())

                val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageBody = requestImage?.let {
                    MultipartBody.Part.createFormData("image", imageFile.name, it)
                }

                editViewModel.editDetailProduct(
                    id = id,
                    name = nameBody,
                    base_price = priceBody,
                    location = cityBody,
                    category_ids = listCategoryRequest,
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
        editViewModel.detailProduct.observe(viewLifecycleOwner) { it ->
            val price = it.data?.body()?.basePrice.toString()
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    val formatter: NumberFormat = DecimalFormat("#,###")
                    val myNumber = price.toInt()
                    val formattedNumber: String = formatter.format(myNumber).toString()
                    //formattedNumber is equal to 1,000,000

                    //sukses mendapat response, progressbar disembunyikan lagi
                    Glide.with(binding.ivPhoto)
                        .load(it.data?.body()?.imageUrl)
                        .error(R.drawable.add_img)
                        .into(binding.ivPhoto)

                    binding.apply {

//                        tvCategory.text = it.data?.body()?.categories?.joinToString {
//                            it.name
//                        }
                        etName.editText?.setText(it.data?.body()?.name)
                        etPrice.editText?.setText("Rp. $formattedNumber")
                        etDescription.editText?.setText(it.data?.body()?.description.toString())
                        etCity.editText?.setText(it.data?.body()?.location)
                        listSelectedCategory.clear()
                        listSelectedCategoryId.clear()
                        it.data?.body()?.categories?.forEach {
                            val category = GetCategoryResponseItem(
                                "",
                                it.id,
                                it.name,
                                ""
                            )
                            listSelectedCategory.add(category)
                            listSelectedCategoryId.add(it.id)
                            chipGroupSelectedCategory.addChip(category)
                        }
                    }
                }
                Status.ERROR -> {
                    val error = it.message
                    Toast.makeText(
                        requireContext(),
                        "Error get Data : $error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        editViewModel.editDetailProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        200 -> {
                            MainFragment.activePage = R.id.main_sale_list
                            MainFragment.statusTerbit = "sukses"
                            findNavController()
                                .navigate(R.id.action_editFragment_to_mainFragment)
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

        editViewModel.getCategoryResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }

                Status.SUCCESS -> {

                    when (it.data?.code()) {
                        200 -> {
//                            it.data.body()?.let { it1 -> listCategory.addAll(it1) }
//                            binding.etCategory.isEnabled = true
                            val rawCategory = it.data.body()
                            listCategory.clear()
                            rawCategory?.forEach { getCategoryResponseItem ->
                                listCategory.add(Pair(false, getCategoryResponseItem))
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