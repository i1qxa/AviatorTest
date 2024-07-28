package com.i1qxa.aviatortest.ui.game

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.i1qxa.aviatortest.R
import com.i1qxa.aviatortest.databinding.FragmentGameBinding
import com.i1qxa.aviatortest.domain.game_data.MoveDirections
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameFragment : Fragment(), OnTouchListener {

    private val binding by lazy { FragmentGameBinding.inflate(layoutInflater) }
    private val viewModel: GameViewModel by viewModels()
    private var xStart = 0F
    private var xEnd = 0F
    private var yStart = 0F
    private var yEnd = 0F
    private var viewSwiped: ImageView? = null
    private val listOfAirplaneViews = mutableListOf<ImageView>()
//    by lazy {
//        listOf(
//            binding.airplane11 as ImageView,
//            binding.airplane12 as ImageView,
//            binding.airplane13 as ImageView,
//            binding.airplane14 as ImageView,
//            binding.airplane21 as ImageView,
//            binding.airplane22 as ImageView,
//            binding.airplane23 as ImageView,
//            binding.airplane24 as ImageView,
//            binding.airplane31 as ImageView,
//            binding.airplane32 as ImageView,
//            binding.airplane33 as ImageView,
//            binding.airplane34 as ImageView,
//            binding.airplane41 as ImageView,
//            binding.airplane42 as ImageView,
//            binding.airplane43 as ImageView,
//            binding.airplane44 as ImageView
//        )
//    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        v?.performClick()
        if (v in listOfAirplaneViews) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    xStart = event.x
                    yStart = event.y
                    viewSwiped = v as ImageView
                }

                MotionEvent.ACTION_MOVE -> {
                    xEnd = event.x
                    yEnd = event.y
                }

                MotionEvent.ACTION_UP -> {
                    val xDiff = xEnd - xStart
                    val yDiff = yEnd - yStart
                    val xMod = if (xDiff < 0) xDiff * (-1) else xDiff
                    val yMod = if (yDiff < 0) yDiff * (-1) else yDiff
                    val moveDirection = if (xMod > yMod) {
                        if (xDiff < 0) MoveDirections.LEFT
                        else MoveDirections.RIGHT
                    } else {
                        if (yDiff < 0) MoveDirections.UP
                        else MoveDirections.DAWN
                    }
                    xStart = 0F
                    xEnd = 0F
                    yStart = 0F
                    yEnd = 0F
                    viewSwiped?.let { viewModel.moveCard(moveDirection, it) }
                    viewSwiped = null
                }
            }
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(100)
            initAirplaneViews()
        }
        observeListOfAirplanes()
    }

    private fun initAirplaneViews(){
        listOfAirplaneViews.addAll(listOf(
            binding.airplane11 as ImageView,
            binding.airplane12 as ImageView,
            binding.airplane13 as ImageView,
            binding.airplane14 as ImageView,
            binding.airplane21 as ImageView,
            binding.airplane22 as ImageView,
            binding.airplane23 as ImageView,
            binding.airplane24 as ImageView,
            binding.airplane31 as ImageView,
            binding.airplane32 as ImageView,
            binding.airplane33 as ImageView,
            binding.airplane34 as ImageView,
            binding.airplane41 as ImageView,
            binding.airplane42 as ImageView,
            binding.airplane43 as ImageView,
            binding.airplane44 as ImageView
        ))
        viewModel.setupListOfAirplanes(listOfAirplaneViews)
        setTouchListener()
    }

    private fun observeListOfAirplanes(){
        viewModel.listOfAirplanesLD.observe(viewLifecycleOwner){ airplaneCardList ->
            airplaneCardList.map { airplaneCard ->
                if (airplaneCard.airplane!=null){
                    airplaneCard.airplaneView.setImageResource(airplaneCard.airplane.lvlAndRes.resId)
                }
            }
        }
    }

    private fun setTouchListener(){
        listOfAirplaneViews.map {
            it.setOnTouchListener(this@GameFragment)
        }
    }

}