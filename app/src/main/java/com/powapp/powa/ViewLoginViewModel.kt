package com.powapp.powa

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.powapp.powa.data.DataEntity
import com.powapp.powa.data.InternalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewLoginViewModel(app: Application) : AndroidViewModel(app) {
    private val database = InternalDatabase.getInstance(app)
    val currentLoginData = MutableLiveData<DataEntity>()

    fun getLoginById(loginId: Int) {
        //Run as coroutine (background thread)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                //Gets the login if exists
                val login =
                    if (loginId != NEW_ENTRY_ID)
                        database?.loginDao()?.getLoginById(loginId)
                    else
                        DataEntity()
                //Posts data from background thread
                currentLoginData.postValue(login)
            }
        }
    }

    fun updateLoginData() {
        currentLoginData.value?.let {
            it.title = it.title.trim()

            //Error prevention
            if (it.id == NEW_ENTRY_ID && it.title.isEmpty())
                return

            //Runs background thread to perform update
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if (it.title.isEmpty()) {
                        database?.loginDao()?.deleteLoginData(it)
                    } else {
                        database?.loginDao()?.insertLogin(it)
                    }
                }
            }
        }
    }
}