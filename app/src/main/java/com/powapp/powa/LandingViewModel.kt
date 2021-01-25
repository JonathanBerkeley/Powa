package com.powapp.powa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.powapp.powa.data.DataEntity
import com.powapp.powa.data.SampleDataProvider

class LandingViewModel : ViewModel() {
    val loginList = MutableLiveData<List<DataEntity>>()

    init {
        loginList.value = SampleDataProvider.Companion.getSampleLogins()
    }
}