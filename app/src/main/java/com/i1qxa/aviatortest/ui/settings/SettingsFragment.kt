package com.i1qxa.aviatortest.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.Coil
import coil.load
import coil.transform.CircleCropTransformation
import com.i1qxa.aviatortest.R
import com.i1qxa.aviatortest.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRoundedImges()
    }

    private fun setupRoundedImges(){
        with(binding){
            ivClassic?.load(R.drawable.game_background_1){
                transformations(coil.transform.RoundedCornersTransformation(50F))
            }
            ivThunder?.load(R.drawable.game_background_2){
                transformations(coil.transform.RoundedCornersTransformation(50F))
            }
            ivBlueSky?.load(R.drawable.game_background_3){
                transformations(coil.transform.RoundedCornersTransformation(50F))
            }
        }
    }

}