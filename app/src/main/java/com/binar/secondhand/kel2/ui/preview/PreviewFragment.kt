package com.binar.secondhand.kel2.ui.preview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentPreviewBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.kel2.utils.URIPathHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.DecimalFormat
import java.text.NumberFormat

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(FragmentPreviewBinding::inflate) {

    val args: PreviewFragmentArgs by navArgs()

    private val previewViewModel: PreviewViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previewViewModel.getAuth()

        profileAuth()

        terbit()

        binding.btnTerbit.setOnClickListener {
            val name = args.name
            val price = args.price
            val description = args.description
            val city = args.location
            val category = args.category
            val image = args.image.toUri()

            val imageFile = if (image == null) {
                null
            } else {
                File(URIPathHelper.getPath(requireContext(), image!!).toString())
            }
            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
            val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
            val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
            val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageBody = requestImage?.let {
                MultipartBody.Part.createFormData("image", imageFile?.name, it)
            }
            previewViewModel.terbit(
                name = nameBody,
                base_price = priceBody,
                location = cityBody,
                category_ids = categoryBody,
                description = descriptionBody,
                image = imageBody
            )
        }
    }

    private fun terbit() {
        previewViewModel.terbit.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    MainFragment.activePage = R.id.main_sale_list
                    MainFragment.statusTerbit = "sukses"
                    findNavController()
                        .navigate(R.id.action_previewFragment_to_mainFragment)

                }
                Status.ERROR -> {
                    Snackbar.make(binding.snackbar, "Produk Gagal Terbit", Snackbar.LENGTH_LONG)
                        .setAction("x") {
                            // Responds to click on the action
                        }
                        .setBackgroundTint(resources.getColor(R.color.Green))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()
                }
                Status.LOADING -> {
                }
            }
        }
    }

    private fun profileAuth() {
        previewViewModel.getAuthResponse.observe(viewLifecycleOwner) {
            val name = args.name
            val price = args.price
            val description = args.description
            val location = args.location
            val category = args.category
            val image = args.image.toUri()

            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        200 -> {
                            val formatter: NumberFormat = DecimalFormat("#,###")
                            val myNumber = price.toInt()
                            val formattedNumber: String = formatter.format(myNumber).toString()
                            //formattedNumber is equal to 1,000,000
                            binding.tvTitle.text = name
                            binding.tvPrice.text = "Rp. $formattedNumber"
                            binding.tvLocation.text = location
                            binding.tvDesc.text = description
                            binding.tvCategory.text = category
                            binding.tvName.text = it.data?.body()?.fullName

                            Glide.with(this)
                                .load(it.data?.body()?.imageUrl)
                                .centerCrop()
                                .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
                                .into(binding.imgProfile)

                            Glide.with(this)
                                .load(image)
                                .centerCrop()
                                .into(binding.ivBackdrop)
                        }
                        else -> {
                            Toast.makeText(
                                requireContext(),
                                "${it.data?.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}