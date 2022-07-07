package com.binar.secondhand.kel2.ui.profile

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentProfileBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.utils.URIPathHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.getKoin
import java.io.File

class ProfileFragment :
    BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    //create instance viewModel
    private val profileViewModel: ProfileViewModel by viewModel()

    private var imageUri : Uri? = null

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

        val city = resources.getStringArray(R.array.city)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, city)
        binding.autoCompleteTv.setAdapter(arrayAdapter)

        //call this method first
        profile()


        profileViewModel.getAuth()

        binding.ivCam.setOnClickListener {
            openImagePicker()
        }

        binding.btnSave.setOnClickListener {
            binding.apply {
                val name = etName.editText?.text.toString()
                val city = etCity.editText?.text.toString()
                val address = etAddress.editText?.text.toString()
                val phoneNumber = etPhone.editText?.text.toString()

                val imageFile = if (imageUri == null){
                    null
                }else{
                    File(URIPathHelper.getPath(requireContext(), imageUri!!).toString())
                }

                val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
                val addressBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
                val phoneNumberBody = phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())

                val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageBody = requestImage?.let { it1 ->
                    MultipartBody.Part.createFormData("image",
                        imageFile.name, it1
                    )
                }

                profileViewModel.putAuth(
                    fullname = nameBody,
                    city = cityBody,
                    address = addressBody,
                    phone_number = phoneNumberBody,
                    image = imageBody
                )
            }
//            findNavController().navigate(R.id.action_profileFragment_to_notificationFragment)
        }

    }

    private fun profile(){

        //only call all the observer here once, do not call the observer somewhere else

        profileViewModel.authPutResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {
                    //loading state, misal menampilkan progressbar
                    binding.pbLoading.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    //sukses mendapat response, progressbar disembunyikan lagi
                    binding.pbLoading.visibility = View.GONE

                    when(it.data?.code()){
                        //jika code response 200
                        200 ->{
                            //if 200 is the response code that contains data
                            //do something with data here, for example, add to recyclerlist

//                            val data = it.data.body() //this is the data
                            Toast.makeText(requireContext(), "edit sukses", Toast.LENGTH_SHORT).show()
                            //we can get the data like this
                            //val result = data
                        }
                        //jika code response 403
                        403 ->{
                            //for example if 403 is wrong response "not authorized"
                            //do something for this code, shoing snackbar for example
                            Toast.makeText(requireContext(), "Error code : 403", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                Status.ERROR ->{
                    //error state
                    //error cause, for example, you can toast it to show error
                    binding.pbLoading.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        profileViewModel.authGetResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {
                    //loading state, misal menampilkan progressbar
                    binding.pbLoading.visibility = View.VISIBLE

                    val shimmer =
                        Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                            .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                            .setBaseAlpha(0.7f) //the alpha of the underlying children
                            .setHighlightAlpha(0.6f) // the shimmer alpha amount
                            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                            .setAutoStart(true)
                            .build()
                    val shimmerDrawable = ShimmerDrawable().apply {
                        setShimmer(shimmer)
                    }

                    Glide.with(this)
                        .load(R.drawable.round_camera)
                        .placeholder(shimmerDrawable)
                        .circleCrop()
                        .into(binding.ivCam)
                }

                Status.SUCCESS -> {

                    binding.pbLoading.visibility = View.GONE

                    when(it.data?.code()){
                        200 ->{
                            val shimmer =
                                Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                                    .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                                    .setBaseAlpha(0.7f) //the alpha of the underlying children
                                    .setHighlightAlpha(0.6f) // the shimmer alpha amount
                                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                                    .setAutoStart(true)
                                    .build()
                            val shimmerDrawable = ShimmerDrawable().apply {
                                setShimmer(shimmer)
                            }

                            if (it.data.body()?.imageUrl == null){
                                if (imageUri == null){
//                                    Glide.with(requireContext())
//                                        .load(it.data.body()?.imageUrl)
//                                        .placeholder(shimmerDrawable)
//                                        .transform(CenterCrop(), RoundedCorners(12))
//                                        .error(R.drawable.rectangle_camera)
//                                        .into(binding.ivCam)
                                    Glide.with(this)
                                        .load(R.drawable.round_camera)
                                        .placeholder(shimmerDrawable)
                                        .circleCrop()
                                        .into(binding.ivCam)
                                }
                            }else{
                                Glide.with(requireContext())
                                    .load(it.data.body()?.imageUrl)
                                    .placeholder(shimmerDrawable)
                                    .circleCrop()
                                    .into(binding.ivCam)
//                                Glide.with(this)
//                                    .load(it.data.body()?.imageUrl)
//                                    .into(binding.ivCam)
                            }
                            binding.etName.editText?.setText(it.data.body()?.fullName)
                            binding.etCity.editText?.setText(it.data.body()?.city)
                            binding.etAddress.editText?.setText(it.data.body()?.address)
                            binding.etPhone.editText?.setText(it.data.body()?.phoneNumber.toString())
                        }
                    }
                }

                Status.ERROR ->{
                    binding.pbLoading.visibility = View.GONE
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop(1f, 1f)
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
                .into(binding.ivCam)
        }
    }

}