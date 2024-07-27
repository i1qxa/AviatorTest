package com.i1qxa.aviatortest.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.i1qxa.aviatortest.R
import com.i1qxa.aviatortest.databinding.FragmentSettingsBinding
import com.i1qxa.aviatortest.domain.BackgroundImg
import com.i1qxa.aviatortest.domain.backgroundIdKey
import com.i1qxa.aviatortest.domain.dataStore
import com.i1qxa.aviatortest.domain.isVibrationOnKey
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRoundedImges()
        observeSelectedBackground()
        setupRadioBtnClickListeners()
        setupBtnBackClickListener()
        observeVibrationState()
        setupSwitchClickListener()
    }

    private fun observeSelectedBackground(){
        lifecycleScope.launch {
            requireContext().dataStore.data.collect{
                val selectedBackground = it[backgroundIdKey]?:BackgroundImg.BLUE_SKY.resId
                clearSelectedRadioButtons()
                when(selectedBackground){
                    BackgroundImg.CLASSIC.resId -> {
                        binding.rbClassic?.setImageResource(R.drawable.red_dot)
                    }
                    BackgroundImg.THUNDERSTORM.resId -> {
                        binding.rbThunder?.setImageResource(R.drawable.red_dot)
                    }
                    BackgroundImg.BLUE_SKY.resId -> {
                        binding.rbBlueSky?.setImageResource(R.drawable.red_dot)
                    }
                }
            }
        }
    }

    private fun setupRadioBtnClickListeners(){
        with(binding){
            rbClassic?.setOnClickListener {
                viewModel.updateSelectedBackground(BackgroundImg.CLASSIC.resId)
            }
            rbThunder?.setOnClickListener {
                viewModel.updateSelectedBackground(BackgroundImg.THUNDERSTORM.resId)
            }
            rbBlueSky?.setOnClickListener {
                viewModel.updateSelectedBackground(BackgroundImg.BLUE_SKY.resId)
            }
        }
    }



    private fun clearSelectedRadioButtons(){
        with(binding){
            rbClassic?.setImageResource(R.drawable.white_dot)
            rbThunder?.setImageResource(R.drawable.white_dot)
            rbBlueSky?.setImageResource(R.drawable.white_dot)
        }
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

    private fun setupBtnBackClickListener(){
        binding.btnBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeVibrationState(){
        lifecycleScope.launch {
            requireContext().dataStore.data.collect{
                val isVibrationOn = it[isVibrationOnKey]?:false
                binding.switchVibration?.isChecked = isVibrationOn
            }
        }
    }

    private fun setupSwitchClickListener(){
        binding.switchVibration?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.updateVibration(isChecked)
        }
    }

}