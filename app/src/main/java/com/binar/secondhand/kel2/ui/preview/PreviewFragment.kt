package com.binar.secondhand.kel2.ui.preview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentPreviewBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.utils.URIPathHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(FragmentPreviewBinding::inflate){

    val args: PreviewFragmentArgs by navArgs()

    private val previewViewModel: PreviewViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previewViewModel.getAuth()

        profileAuth()

        binding.btnTerbit.setOnClickListener {
            val name = args.name
            val price = args.price
            val description = args.description
            val location = args.location
            val category = args.category
            val image = args.image.toUri()

            val imageFile = if(image == null) {
                null
            }else{
                File(URIPathHelper.getPath(requireContext(), image!!).toString())
            }
            val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageBody = requestImage?.let{
                MultipartBody.Part.createFormData("image", imageFile?.name, it)
            }
            previewViewModel.terbit(
                name.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                price.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                description.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                category.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                location.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                image = imageBody
            )
        }
    }

    private fun profileAuth() {
       previewViewModel.getAuthResponse.observe(viewLifecycleOwner){
           val name = args.name
           val price = args.price
           val description = args.description
           val location = args.location
           val category = args.category
           val image = args.image.toUri()

           when(it.status){
               Status.LOADING -> {
               }
               Status.SUCCESS -> {
                   when(it.data?.code()){
                       200 -> {
                           binding.tvTitle.text = name
                           binding.tvPrice.text = price
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
                           Toast.makeText(requireContext(), "${it.data?.message()}", Toast.LENGTH_SHORT).show()
                       }
                   }
               }
           }
       }
    }
}