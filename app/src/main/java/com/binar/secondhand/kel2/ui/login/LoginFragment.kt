package com.binar.secondhand.kel2.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentLoginBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModel()

    companion object {
        const val LOGINUSER = "login_user"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()

        binding.tvDaftarDisini.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnMasuk.setOnClickListener {
            val loginPostRequest = PostLoginRequest(
                binding.etEmail.text.toString(),
                binding.etMasukkanPassowrd.text.toString()
            )

            if (binding.etEmail.text.isNullOrEmpty() || binding.etMasukkanPassowrd.text.isNullOrEmpty()) {
                Toast.makeText(context, "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else {
                loginViewModel.postLogin(loginPostRequest)
            }
        }
    }

    private fun setUpObserver() {

        val preferences = this.requireActivity().getSharedPreferences(LOGINUSER, Context.MODE_PRIVATE)

        loginViewModel.loginPostResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    binding.pbLoading.visibility = View.GONE
                    when (it.data?.code()) {
                        //jika code response 200
                        201 -> {
                            val data = it.data.body() //this is the data

                            val name = data?.name
                            val email = data?.email
                            val accesToken = data?.accessToken

                            // Save shared preferences
                            val editor = preferences.edit()
                            editor.putString(EMAIL, email)
                            editor.putString(TOKEN, accesToken)
                            editor.apply()

                            // Bisa diganti pindah fragment
                            //Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()

                            //set access token setelah dapet dari login
                            getKoin().setProperty("access_token", accesToken.toString())

//                            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                        }

                        401 -> {
                            showSnackbar("Email or Password Are Wrong")
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