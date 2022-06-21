package com.binar.secondhand.kel2.uii.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class BaseFragment<B : ViewBinding>(private val inflate: Inflate<B>): Fragment() {

    private lateinit var _binding: B
    protected val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    internal fun showSnackbar(@StringRes message: Int) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

    internal fun showSnackbar(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

    internal fun showSnackbarWithAction(
        @StringRes message: Int,
        @StringRes actionText: Int,
        action: () -> Any
    ) {
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.show()
    }

    internal fun showSnackbarWithAction(
        message: String,
        actionText: String,
        action: () -> Any
    ) {
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.show()
    }

}