package com.meehawek.lsmprojekt.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.meehawek.lsmprojekt.viewmodels.BaseViewModel

open class BaseFragment : Fragment() {
    internal fun observeModelNavigation(model : BaseViewModel) {
        model.navigateToFragment.observe(this.viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(it.navigateAction, it.getParametersAsBundle())
            }
        })
    }
}