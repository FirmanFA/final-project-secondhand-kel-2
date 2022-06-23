package com.binar.secondhand.kel2.ui.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.databinding.FragmentNotificationBinding
import com.binar.secondhand.kel2.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {

    private val notificationViewModel: NotificationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notification()
        notificationViewModel.getNotification()
    }

    private fun notification() {
        notificationViewModel.notificationResponse.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    val adapter = NotificationAdapter(object : NotificationAdapter.OnClickListener{
                        override fun onClickItem(data: GetNotificationResponse.GetNotificationResponseItem) {
                            val id = data.id
                        }
                    })
                    adapter.submitData(it.data?.body())
                    binding.rvNotification.adapter = adapter
                }
                Status.ERROR -> {
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}