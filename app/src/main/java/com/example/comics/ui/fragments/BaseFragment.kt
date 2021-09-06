package com.example.comics.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment

abstract class BaseFragment(id: Int) : Fragment(id) {

    fun isInternetOn(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}