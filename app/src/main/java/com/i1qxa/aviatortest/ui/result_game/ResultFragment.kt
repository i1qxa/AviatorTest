package com.i1qxa.aviatortest.ui.result_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.i1qxa.aviatortest.databinding.FragmentResultBinding
import com.i1qxa.aviatortest.domain.launchNewFragment
import com.i1qxa.aviatortest.ui.game.GameFragment

private const val IS_WIN = "is_win"

class ResultFragment : Fragment() {

    private var isWin: Boolean = false
    private val binding by lazy { FragmentResultBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isWin = it.getBoolean(IS_WIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showResult()
        setBtnContinueClickListener()
    }

    private fun showResult(){
        if (isWin){
            binding.tvTryAgain?.visibility = View.GONE
            binding.tvGreat?.visibility = View.VISIBLE
            binding.imgWin?.visibility = View.VISIBLE
        }else{
            binding.tvTryAgain?.visibility = View.VISIBLE
            binding.tvGreat?.visibility = View.GONE
            binding.imgWin?.visibility = View.GONE
        }
    }

    private fun setBtnContinueClickListener(){
        binding.btnContinue?.setOnClickListener {
            parentFragmentManager.launchNewFragment(GameFragment())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(isWin: Boolean) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_WIN, isWin)
                }
            }
    }
}