package com.binar.secondhand.kel2.ui.lengkapi

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentSellerDetailProductBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class SellerDetailProductFragment :
    BaseFragment<FragmentSellerDetailProductBinding>(FragmentSellerDetailProductBinding::inflate) {

    private val sellerDetailProductViewModel: SellerDetailProductViewModel by viewModel()

    private var imageUri : Uri?= null

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
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_sell

        setUpObservers()

        binding.ivPhoto.setOnClickListener {
            openImagePicker()
        }

        binding.btnTerbit.setOnClickListener {
            val terbitPostRequest = PostProductRequest(
                binding.etName.editText?.text.toString(),
                binding.etPrice.editText?.text.toString(),
                binding.etCategory.editText?.text.toString().toInt(),
                List(1, {1}),
                binding.etCity.editText?.text.toString(),
                ""
            )

            if (binding.etName.editText?.text.toString().isEmpty() || binding.etPrice.editText?.text.toString().isEmpty() || binding.etCategory.editText?.text.toString().isEmpty() || binding.etCity.editText?.text.toString().isEmpty()) {
                Toast.makeText(context,"Semua field harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                sellerDetailProductViewModel.postProduct(terbitPostRequest)
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
        sellerDetailProductViewModel.sellerPostProduct.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    Toast.makeText(context,"Berhasil terbit", Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(R.id.)
                }
                Status.ERROR -> {
                    Toast.makeText(context,"Gagal terbit", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}