package com.i1qxa.aviatortest.domain

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