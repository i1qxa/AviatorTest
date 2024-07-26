package com.i1qxa.aviatortest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.i1qxa.aviatortest.domain.BASE_DELAY_IN_SECONDS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    private val _shouldStartGame = MutableLiveData<Any>()
    val shouldStartGame:LiveData<Any>
        get() = _shouldStartGame

    init {
        viewModelScope.launch {
            var timer = 0
            while (timer< BASE_DELAY_IN_SECONDS){
                delay(1000)
                timer++
            }
            _shouldStartGame.postValue(Unit)
        }
    }

}