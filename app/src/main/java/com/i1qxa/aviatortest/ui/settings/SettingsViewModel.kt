package com.i1qxa.aviatortest.ui.settings

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.i1qxa.aviatortest.domain.backgroundIdKey
import com.i1qxa.aviatortest.domain.dataStore
import com.i1qxa.aviatortest.domain.isVibrationOnKey
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application):AndroidViewModel(application) {

    val context = application.applicationContext
    fun updateSelectedBackground(imgId:Int){
        viewModelScope.launch {
            context.dataStore.edit {
                it[backgroundIdKey] = imgId
            }
        }
    }
    fun updateVibration(isVibrationOn:Boolean){
        viewModelScope.launch {
            context.dataStore.edit {
                it[isVibrationOnKey] = isVibrationOn
            }
        }
    }

}