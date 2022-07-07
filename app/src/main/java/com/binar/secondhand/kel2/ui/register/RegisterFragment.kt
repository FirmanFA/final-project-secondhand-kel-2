package com.binar.secondhand.kel2.ui.register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentRegisterBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()

        binding.tvSignin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnSignup.setOnClickListener {
            val registerPostRequest = PostRegisterRequest(
                binding.etNama.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassowrd.text.toString(),
                0,"-",R.drawable.default_profile.toString(),"-"
            )

            if (binding.etNama.text.isNullOrEmpty() || binding.etEmail.text.isNullOrEmpty() || binding.etPassowrd.text.isNullOrEmpty()) {
                Toast.makeText(context, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else if (binding.etPassowrd.text.toString().length < 6) {
                Toast.makeText(context, "Password minimal 6 character", Toast.LENGTH_SHORT).show()
            }
            else if (binding.etPassowrd.text.toString().lowercase() != binding.etKonfirmasiPassowrd.text.toString().lowercase()) {
                Toast.makeText(context, "Password tidak sama", Toast.LENGTH_SHORT).show()
            }
            else {
                registerViewModel.postRegister(registerPostRequest)
            }
        }

        binding.tvPrivacyPolicy.setOnClickListener {
            directToUri()
        }

    }

    private fun directToUri() {
            val i = Intent(
                Intent.ACTION_VIEW, Uri.parse("https://www.privacypolicies.com/live/087ce8d0-b996-4de8-b969-16b4e50fb014")
            )
            startActivity(i)
    }

    private fun setUpObserver() {

        registerViewModel.registerPostResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    binding.pbLoading.visibility = View.GONE
                    when (it.data?.code()) {
                        201 -> {
                            val data = it.data.body()

                            val id = data?.id
                            val fullName = data?.fullName
                            val email = data?.email
                            val password = data?.password
                            val phoneNumber = data?.phoneNumber
                            val address = data?.address
                            val imageUrl = data?.imageUrl
                            val createdAt = data?.createdAt
                            val updatedAt = data?.updatedAt

                            Toast.makeText(context, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }

                        400 -> {
                            showSnackbar("Email Already Exists")
                        }

                        500 -> {
                            showSnackbar("Internal Service Error")
                        }
                    }
                }

                Status.ERROR -> {
                    binding.pbLoading.visibility = View.GONE

                    val error = it.message
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}