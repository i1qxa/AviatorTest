package com.i1qxa.aviatortest.ui.start_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.i1qxa.aviatortest.R
import com.i1qxa.aviatortest.databinding.FragmentStartGameBinding
import com.i1qxa.aviatortest.domain.dataStore
import com.i1qxa.aviatortest.domain.highScoreKey
import com.i1qxa.aviatortest.domain.launchNewFragment
import com.i1qxa.aviatortest.ui.game.GameFragment
import com.i1qxa.aviatortest.ui.settings.SettingsFragment
import kotlinx.coroutines.launch

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
        observeHighScore()
    }

    private fun setupBtnClickListeners(){
        binding.btnSettings?.setOnClickListener {
            parentFragmentManager.launchNewFragment(SettingsFragment())
        }
        binding.btnStart?.setOnClickListener {
            parentFragmentManager.launchNewFragment(GameFragment())
        }
    }

    private fun observeHighScore(){
        lifecycleScope.launch {
            requireContext().dataStore.data.collect{
                val highScore = it[highScoreKey]?:0
                binding.tvHighScore?.text = requireContext().getString(R.string.high_score, highScore)
            }
        }
    }

}