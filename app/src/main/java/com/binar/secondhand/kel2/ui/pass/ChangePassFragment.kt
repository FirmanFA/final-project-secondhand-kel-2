package com.binar.secondhand.kel2.ui.pass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.binar.secondhand.kel2.R
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentChangePassBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePassFragment : BaseFragment<FragmentChangePassBinding>(FragmentChangePassBinding::inflate){

    private val changePassViewModel: ChangePassViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        password()

        binding.btnGanti.setOnClickListener{
            val changePassReq = PutPassRequest(
                binding.etOldPass.text.toString(),
                binding.etPassowrd.text.toString(),
                binding.etKonfirmasiPassowrd.text.toString()
            )
            if (binding.etOldPass.text.isNullOrEmpty() || binding.etPassowrd.text.isNullOrEmpty() || binding.etKonfirmasiPassowrd.text.isNullOrEmpty()) {
                Toast.makeText(context, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else if (binding.etPassowrd.text.toString().length < 6) {
                Toast.makeText(context, "Password minimal 6 character", Toast.LENGTH_SHORT).show()
            }
            else if (binding.etPassowrd.text.toString().lowercase() != binding.etKonfirmasiPassowrd.text.toString().lowercase()) {
                Toast.makeText(context, "Password tidak sama", Toast.LENGTH_SHORT).show()
            }
            else{
                changePassViewModel.putChangePass(changePassReq)
            }
        }
    }

    private fun password() {
        changePassViewModel.putChangePassResponse.observe(viewLifecycleOwner){
            when (it.status){

                Status.LOADING ->{}
                Status.SUCCESS -> {
                    Toast.makeText(context, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                    activity?.onBackPressed()
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}