package com.powapp.powa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powapp.powa.data.DataEntity
import com.powapp.powa.data.SampleDataProvider
import kotlinx.coroutines.launch

class LandingViewModel : ViewModel() {
    val loginList = MutableLiveData<List<DataEntity>>()

    private val _response = MutableLiveData<String>()
    private val _property = MutableLiveData<DataEntity>()

    val response: LiveData<String>
        get() = _response

    val property: LiveData<DataEntity>
        get() = _property

    init {
        loginList.value = SampleDataProvider.Companion.getSampleLogins()
    }

    private fun insertFavicons() {
        viewModelScope.launch {
        }
    }
}