package com.i1qxa.aviatortest.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.i1qxa.aviatortest.R

fun FragmentManager.launchNewFragment(newFragment:Fragment){
    this.beginTransaction().apply {
        replace(R.id.mainContainer, newFragment)
        addToBackStack(null)
        commit()
    }
}
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AVIATOR_DS)
