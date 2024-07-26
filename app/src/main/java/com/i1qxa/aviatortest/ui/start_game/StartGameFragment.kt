package com.i1qxa.aviatortest.ui.start_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.i1qxa.aviatortest.databinding.FragmentStartGameBinding
import com.i1qxa.aviatortest.domain.launchNewFragment
import com.i1qxa.aviatortest.ui.settings.SettingsFragment

class StartGameFragment : Fragment() {

    private val binding by lazy { FragmentStartGameBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnClickListeners()
    }

    private fun setupBtnClickListeners(){
        binding.btnSettings?.setOnClickListener {
            parentFragmentManager.launchNewFragment(SettingsFragment())
        }
    }

}