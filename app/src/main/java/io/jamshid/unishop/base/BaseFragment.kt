package io.jamshid.unishop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.jamshid.unishop.presentation.MainActivity
import io.jamshid.unishop.presentation.feature_main.dialog.NoConnectionDialog

// Created by Usmon Abdurakhmanv on 5/13/2022.

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    protected val isConnected get() = (activity as MainActivity).isConnected()

    open fun myCreate(savedStateBundle: Bundle?) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myCreate(savedInstanceState)
    }

    abstract fun myCreateView(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        if (_binding == null)
            throw IllegalStateException("View binding couldn't be null")
        myCreateView(savedInstanceState)
        checkInternet()
        return binding.root
    }

    protected fun checkInternet(): Boolean {
        if (!(activity as MainActivity).isConnected()) {
            val dialog = NoConnectionDialog("No internet")
            dialog.show(requireActivity().supportFragmentManager, dialog.tag)
            dialog.setOnDismissListener {
                checkInternet()
            }
            return false
        }
        return true
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun showProgress(show: Boolean) {
        (activity as MainActivity).showProgress(show)
    }
}