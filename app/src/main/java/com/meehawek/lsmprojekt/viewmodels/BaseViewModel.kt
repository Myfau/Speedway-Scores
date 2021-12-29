package com.meehawek.lsmprojekt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meehawek.lsmprojekt.ui.base.NavigEvent
import com.meehawek.lsmprojekt.ui.base.NavigateEventInfo

open class BaseViewModel : ViewModel() {
    internal val _navigateToFragment = MutableLiveData<NavigEvent<NavigateEventInfo>>()
    val navigateToFragment: LiveData<NavigEvent<NavigateEventInfo>>
        get() = _navigateToFragment

}
